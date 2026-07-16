package com.gta.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gta.dto.HangarOrderUpdateRequest;
import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportSlotDto;
import com.gta.dto.OwnedTransportUpdateRequest;
import com.gta.dto.SwapOwnedTransportRequest;
import com.gta.exception.BusinessException;
import com.gta.mapper.OwnedTransportMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnedTransportServiceImpl implements OwnedTransportService {
	private final OwnedTransportMapper ownedTransportMapper;
	
	@Value("${upload.owned-transport.path}")
	private String uploadPath;
	
	private static final String STORAGE_TYPE_GARAGE = "GARAGE";
	private static final String STORAGE_TYPE_HANGAR = "HANGAR";
	private static final String STORAGE_TYPE_HANGAR_STORAGE = "HANGAR_STORAGE";
	private static final String STORAGE_TYPE_HANGAR_VINEWOOD = "HANGAR_VINEWOOD";

	private static final int HANGAR_STORAGE_LIMIT = 20;
	private static final int HANGAR_VINEWOOD_LIMIT = 10;
	
	/**
	 * 보유 이동수단 목록 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<OwnedTransportListDto> getList(Long userId)
	{
	    return ownedTransportMapper.selectList(userId);
	}
	
	/**
	 * 보유 이동수단 상세 조회
	 */
	@Override
	public OwnedTransportListDto getDetail(Long userId, Long ownedId) {
		return ownedTransportMapper.getDetail(userId, ownedId);
	}

	/**
	 * 보유 이동수단 등록
	 * @param userId
	 * @param req
	 * @return
	 */
	@Override
	@Transactional
	public int create(Long userId, OwnedTransportCreateRequest req) {
		// 기본값 처리
	    if (req.getOwnStatus() == null || req.getOwnStatus().trim().isEmpty()) {
	        req.setOwnStatus("보유");
	    }
	    
	    if (req.getAcquiredYn() == null || req.getAcquiredYn().trim().isEmpty()) {
	        req.setAcquiredYn("Y");
	    }
	    
	    req.setUserId(userId);
	    
	    String storageType = req.getStorageType();
	    
	    if (storageType == null || storageType.trim().isEmpty()) {
	        req.setStorageType("UNASSIGNED");
	        storageType = req.getStorageType();
	    }
	    
	    // 보관 타입별 차고/슬롯 검증
	    validateStorageForCreate(req);
		
	    // owned_transport insert (ownedId 생성)
	    int inserted = ownedTransportMapper.insertOwnedTransport(req);

	    // insert 실패면 바로 종료
	    if (inserted <= 0) {
	        return inserted;
	    }

	    if (req.getOwnedId() == null) {
	        throw new IllegalStateException("ownedId 생성값을 받지 못했습니다. Mapper XML의 키 설정을 확인하세요.");
	    }
	    
	    // GARAGE 인 경우만 슬롯 위치 저장
	    if (isLocationStorage(req.getStorageType())) {
	        int storageInserted = ownedTransportMapper.insertOwnedTransportStorage(req);

	        if (storageInserted == 0) {
	            throw new IllegalStateException(
	                "보관 위치 등록에 실패했습니다. ownedId=" + req.getOwnedId()
	            );
	        }
	    }

	    return inserted;
	}

	/**
	 * 보유 이동수단 수정
	 * @param userId
	 * @param ownedId
	 * @param request
	 */
	@Override
	@Transactional
	public void update(Long userId, Long ownedId, OwnedTransportUpdateRequest request) {
		// 기존 이미지 URL 조회
		String oldImageUrl = ownedTransportMapper.selectImageUrl(ownedId, userId);
		
		if (request.getAcquiredYn() == null || request.getAcquiredYn().trim().isEmpty()) {
		    request.setAcquiredYn("Y");
		}
		
	    String storageType = request.getStorageType();

	    if (storageType == null || storageType.trim().isEmpty()) {
	        storageType = "UNASSIGNED";
	        request.setStorageType(storageType);
	    }

	    request.setOwnedId(ownedId);
	    request.setUserId(userId);
	    
	    // 보관 타입별 차고/슬롯 검증
	    validateStorageForUpdate(request);
	    
	    ownedTransportMapper.updateStorageType(request);
	    
	    // 이미지 변경/삭제 시 기존 파일 삭제
	    String newImageUrl = request.getImageUrl();
	    
	    if (newImageUrl != null && oldImageUrl != null && !oldImageUrl.isEmpty()) {
	    	boolean isRemoved = newImageUrl.isEmpty();
	        boolean isChanged = !newImageUrl.equals(oldImageUrl);

	        if (isRemoved || isChanged) {
	            try {
	                String fileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
	                
	                File originalFile = new File(uploadPath, fileName);
	                
	                String thumbName = fileName.substring(0, fileName.lastIndexOf('.'))
	                        + "_thumb"
	                        + fileName.substring(fileName.lastIndexOf('.'));
	                
	                File thumbFile = new File(uploadPath, thumbName);
	                
	                if (originalFile.exists()) {
	                    originalFile.delete();
	                }

	                if (thumbFile.exists()) {
	                    thumbFile.delete();
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    if (!"GARAGE".equals(storageType)) {
	        ownedTransportMapper.deleteByOwnedId(ownedId, userId);
	        return;
	    }

	    int exists = ownedTransportMapper.existsByOwnedId(ownedId, userId);

	    Long occupiedOwnedId = ownedTransportMapper.selectOwnedIdByGarageAndSlot(
	        request.getGarageId(),
	        request.getSlotNo(),
	        userId
	    );

	    if (occupiedOwnedId != null && !occupiedOwnedId.equals(ownedId)) {
	        throw new IllegalStateException(
	            "이미 사용 중인 슬롯입니다. garageId=" + request.getGarageId() + ", slotNo=" + request.getSlotNo()
	        );
	    }

	    if (exists == 0) {
	        int inserted = ownedTransportMapper.insertLocation(
	            ownedId,
	            request.getGarageId(),
	            request.getSlotNo(),
	            userId
	        );

	        if (inserted == 0) {
	            throw new IllegalStateException("보관 위치 등록에 실패했습니다. ownedId=" + ownedId);
	        }

	        return;
	    }

	    int updated = ownedTransportMapper.updateLocation(
	        ownedId,
	        request.getGarageId(),
	        request.getSlotNo(),
	        userId
	    );

	    if (updated == 0) {
	        throw new IllegalStateException("이동 처리에 실패했습니다. ownedId=" + ownedId);
	    }
	}

	/**
	 * 보유 이동수단 삭제
	 * @param userId
	 * @param ownedId
	 */
	@Override
	@Transactional
	public void delete(Long userId, Long ownedId) {
		String imageUrl = ownedTransportMapper.selectImageUrl(ownedId, userId);
		
		if (imageUrl != null && !imageUrl.isEmpty()) {
		    try {
		    	String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

		    	File originalFile = new File(uploadPath, fileName);

		    	String thumbName = fileName.substring(0, fileName.lastIndexOf('.'))
		    	        + "_thumb"
		    	        + fileName.substring(fileName.lastIndexOf('.'));

		    	File thumbFile = new File(uploadPath, thumbName);

		    	if (originalFile.exists()) {
		    	    originalFile.delete();
		    	}

		    	if (thumbFile.exists()) {
		    	    thumbFile.delete();
		    	}
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		int deleted = ownedTransportMapper.deleteById(ownedId, userId);
		
		if (deleted == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제 대상이 없습니다.");
		}
	}

	/**
	 * 보유 이동수단 슬롯 위치 교체
	 * @param userId
	 * @param request
	 */
	@Override
	@Transactional
	public void swapOwnedTransport(Long userId, SwapOwnedTransportRequest request) {
		if (request == null) {
	        throw new BusinessException("요청값이 없습니다.");
	    }
		
		Long sourceOwnedId = request.getSourceOwnedId();
		Long targetOwnedId = request.getTargetOwnedId();
		
		if (sourceOwnedId == null || targetOwnedId == null) {
	        throw new BusinessException("교체 대상 차량 ID가 없습니다.");
	    }

	    if (sourceOwnedId.equals(targetOwnedId)) {
	        throw new BusinessException("같은 차량끼리는 교체할 수 없습니다.");
	    }
	    
	    OwnedTransportSlotDto source = ownedTransportMapper.selectStorageByOwnedId(sourceOwnedId, userId);
	    OwnedTransportSlotDto target = ownedTransportMapper.selectStorageByOwnedId(targetOwnedId, userId);
	    
	    if (source == null || target == null) {
	        throw new BusinessException("교체 대상 차량 정보를 찾을 수 없습니다.");
	    }
	    
	    ownedTransportMapper.updateStorageSlotByOwnedId(
    	    sourceOwnedId,
    	    source.getGarageId(),
    	    -1,
    	    userId
    	);

    	ownedTransportMapper.updateStorageSlotByOwnedId(
    	    targetOwnedId,
    	    source.getGarageId(),
    	    source.getSlotNo(),
    	    userId
    	);

    	ownedTransportMapper.updateStorageSlotByOwnedId(
    	    sourceOwnedId,
    	    target.getGarageId(),
    	    target.getSlotNo(),
    	    userId
    	);
	}
	
	/**
	 * 격납층 정렬 순서 저장
	 * @param userId
	 * @param requestList
	 */
	@Override
	@Transactional
	public void updateHangarOrder(Long userId, List<HangarOrderUpdateRequest> requestList)
	{
	    if (requestList == null || requestList.isEmpty()) {
	        return;
	    }

	    for (HangarOrderUpdateRequest request : requestList) {
	        ownedTransportMapper.updateHangarOrder(userId, request);
	    }
	}

	/**
	 * 등록 요청 보관 타입 검증
	 */
	private void validateStorageForCreate(OwnedTransportCreateRequest req)
	{
		String storageType = req.getStorageType();

	    if (STORAGE_TYPE_GARAGE.equals(storageType)) {
	        if (req.getGarageId() == null || req.getSlotNo() == null) {
	            throw new BusinessException("차고 보관은 차고와 슬롯이 모두 필요합니다.");
	        }

	        return;
	    }

	    if (isHangarRelatedStorage(storageType)) {
	        if (req.getGarageId() != null || req.getSlotNo() != null) {
	            throw new BusinessException("격납고 보관은 차고와 슬롯을 저장하지 않습니다.");
	        }

	        validateHangarAvailableTransport(req.getModelId());
	        validateHangarStorageLimitForCreate(req.getUserId(), storageType);
	        req.setSlotNo(null);

	        return;
	    }

	    if (req.getGarageId() != null || req.getSlotNo() != null) {
	        throw new BusinessException("차고/격납고 보관이 아닌 경우 차고와 슬롯은 저장할 수 없습니다.");
	    }
	}

	/**
	 * 수정 요청 보관 타입 검증
	 */
	private void validateStorageForUpdate(OwnedTransportUpdateRequest request)
	{
		String storageType = request.getStorageType();

	    if (STORAGE_TYPE_GARAGE.equals(storageType)) {
	        if (request.getGarageId() == null || request.getSlotNo() == null) {
	            throw new BusinessException("차고 보관은 차고와 슬롯이 모두 필요합니다.");
	        }

	        return;
	    }

	    if (isHangarRelatedStorage(storageType)) {
	        if (request.getGarageId() != null || request.getSlotNo() != null) {
	            throw new BusinessException("격납고 보관은 차고와 슬롯을 저장하지 않습니다.");
	        }

	        request.setSlotNo(null);

	        validateHangarAvailableTransportByOwnedId(request.getOwnedId());
	        validateHangarStorageLimitForUpdate(
	            request.getUserId(),
	            request.getOwnedId(),
	            storageType
	        );

	        return;
	    }

	    if (request.getGarageId() != null || request.getSlotNo() != null) {
	        throw new BusinessException("차고/격납고 보관이 아닌 경우 차고와 슬롯은 저장할 수 없습니다.");
	    }
	}
	
	/**
	 * 차고/격납고 보관 타입 여부
	 * @param storageType
	 * @return
	 */
	private boolean isLocationStorage(String storageType)
	{
		return STORAGE_TYPE_GARAGE.equals(storageType);
	}
	
	/**
	 * 격납고 관련 보관 타입 여부
	 * @param storageType
	 * @return
	 */
	private boolean isHangarRelatedStorage(String storageType)
	{
	    return STORAGE_TYPE_HANGAR.equals(storageType)
	        || STORAGE_TYPE_HANGAR_STORAGE.equals(storageType)
	        || STORAGE_TYPE_HANGAR_VINEWOOD.equals(storageType);
	}

	/**
	 * 개수 제한 대상 격납고 보관 타입 여부
	 * @param storageType
	 * @return
	 */
	private boolean isLimitedHangarStorage(String storageType)
	{
	    return STORAGE_TYPE_HANGAR_STORAGE.equals(storageType)
	        || STORAGE_TYPE_HANGAR_VINEWOOD.equals(storageType);
	}

	/**
	 * 격납고 보관 타입별 제한 개수 조회
	 * @param storageType
	 * @return
	 */
	private int getHangarStorageLimit(String storageType)
	{
	    if (STORAGE_TYPE_HANGAR_STORAGE.equals(storageType)) {
	        return HANGAR_STORAGE_LIMIT;
	    }

	    if (STORAGE_TYPE_HANGAR_VINEWOOD.equals(storageType)) {
	        return HANGAR_VINEWOOD_LIMIT;
	    }

	    return Integer.MAX_VALUE;
	}

	/**
	 * 격납고 보관 타입 표시명 조회
	 * @param storageType
	 * @return
	 */
	private String getHangarStorageName(String storageType)
	{
	    if (STORAGE_TYPE_HANGAR_STORAGE.equals(storageType)) {
	        return "격납고 저장소";
	    }

	    if (STORAGE_TYPE_HANGAR_VINEWOOD.equals(storageType)) {
	        return "격납고 바인우드 클럽 보관소";
	    }

	    return "격납고 격납층";
	}

	/**
	 * 등록 시 격납고 보관 타입별 제한 개수 검증
	 * @param userId
	 * @param storageType
	 */
	private void validateHangarStorageLimitForCreate(Long userId, String storageType)
	{
	    if (!isLimitedHangarStorage(storageType)) {
	        return;
	    }

	    int limit = getHangarStorageLimit(storageType);
	    int currentCount = ownedTransportMapper.countByStorageType(userId, storageType);

	    if (currentCount >= limit) {
	        throw new BusinessException(getHangarStorageName(storageType) + "는 최대 " + limit + "대까지 보관할 수 있습니다.");
	    }
	}

	/**
	 * 수정 시 격납고 보관 타입별 제한 개수 검증
	 * @param userId
	 * @param ownedId
	 * @param storageType
	 */
	private void validateHangarStorageLimitForUpdate(Long userId, Long ownedId, String storageType)
	{
	    if (!isLimitedHangarStorage(storageType)) {
	        return;
	    }

	    int limit = getHangarStorageLimit(storageType);
	    int currentCount = ownedTransportMapper.countByStorageType(userId, storageType);

	    if (currentCount >= limit) {
	        throw new BusinessException(getHangarStorageName(storageType) + "는 최대 " + limit + "대까지 보관할 수 있습니다.");
	    }
	}
	
	/**
	 * modelId 기준 격납고 보관 가능 이동수단 검증
	 * @param modelId
	 */
	private void validateHangarAvailableTransport(Long modelId)
	{
	    if (modelId == null) {
	        throw new BusinessException("이동수단 모델 ID가 없습니다.");
	    }

	    String features = ownedTransportMapper.selectFeaturesByModelId(modelId);

	    if (!hasHangarFeature(features)) {
	        throw new BusinessException("격납고에는 격납고 보관 가능 이동수단만 등록할 수 있습니다.");
	    }
	}

	/**
	 * ownedId 기준 격납고 보관 가능 이동수단 검증
	 * @param ownedId
	 */
	private void validateHangarAvailableTransportByOwnedId(Long ownedId)
	{
	    if (ownedId == null) {
	        throw new BusinessException("보유 이동수단 ID가 없습니다.");
	    }

	    String features = ownedTransportMapper.selectFeaturesByOwnedId(ownedId);

	    if (!hasHangarFeature(features)) {
	        throw new BusinessException("격납고에는 격납고 보관 가능 이동수단만 등록할 수 있습니다.");
	    }
	}

	/**
	 * features 내 격납고 보관 가능 코드 존재 여부
	 * @param features
	 * @return
	 */
	private boolean hasHangarFeature(String features)
	{
	    if (features == null || features.trim().isEmpty()) {
	        return false;
	    }

	    return Arrays.stream(features.split(","))
	        .map(String::trim)
	        .anyMatch((feature) -> {
	            return "HGS".equals(feature)
	                || "HGM".equals(feature)
	                || "HGL".equals(feature)
	                || "HGX".equals(feature);
	        });
	}
}
