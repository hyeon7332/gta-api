package com.gta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportSlotDto;
import com.gta.dto.OwnedTransportUpdateRequest;
import com.gta.dto.SwapOwnedTransportRequest;
import com.gta.mapper.OwnedTransportMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnedTransportServiceImpl implements OwnedTransportService {
	private final OwnedTransportMapper ownedTransportMapper;
	
	@Value("${upload.owned-transport.path}")
	private String uploadPath;
	
	@Override
	public List<OwnedTransportListDto> getList(Long userId)
	{
	    return ownedTransportMapper.selectList(userId);
	}

	@Override
	public int create(Long userId, OwnedTransportCreateRequest req) {
		// 기본값 처리
	    if (req.getOwnStatus() == null || req.getOwnStatus().trim().isEmpty()) {
	        req.setOwnStatus("보유");
	    }
	    
	    req.setUserId(userId);
	    
	    String storageType = req.getStorageType();
	    
	    if (storageType == null || storageType.trim().isEmpty()) {
	        req.setStorageType("UNASSIGNED");
	        storageType = req.getStorageType();
	    }
	    
	    // 차고/슬롯 검증
	    if ("GARAGE".equals(storageType)) {
	        if (req.getGarageId() == null || req.getSlotNo() == null) {
	            throw new IllegalArgumentException("차고 보관은 차고와 슬롯이 모두 필요합니다.");
	        }
	    } else {
	        if (req.getGarageId() != null || req.getSlotNo() != null) {
	            throw new IllegalArgumentException("차고 보관이 아닌 경우 차고와 슬롯은 저장할 수 없습니다.");
	        }
	    }
		
	    // owned_transport insert (ownedId 생성)
	    int inserted = ownedTransportMapper.insertOwnedTransport(req);

	    // insert 실패면 바로 종료
	    if (inserted <= 0) {
	        return inserted;
	    }

	    if (req.getOwnedId() == null) {
	        throw new IllegalStateException("ownedId 생성값을 받지 못했습니다. Mapper XML의 키 설정을 확인하세요.");
	    }
	    
	    // GARAGE 인 경우만 storage insert
	    if ("GARAGE".equals(req.getStorageType())) {
	        ownedTransportMapper.insertOwnedTransportStorage(req);
	    }

	    return inserted;
	}

	@Override
	@Transactional
	public void update(Long userId, Long ownedId, OwnedTransportUpdateRequest request) {
		// 기존 이미지 URL 조회
		String oldImageUrl = ownedTransportMapper.selectImageUrl(ownedId, userId);
		
	    String storageType = request.getStorageType();

	    if (storageType == null || storageType.trim().isEmpty()) {
	        storageType = "UNASSIGNED";
	        request.setStorageType(storageType);
	    }

	    if ("GARAGE".equals(storageType)) {
	        if (request.getGarageId() == null || request.getSlotNo() == null) {
	            throw new IllegalArgumentException("차고 보관은 차고와 슬롯이 모두 필요합니다.");
	        }
	    } else {
	        if (request.getGarageId() != null || request.getSlotNo() != null) {
	            throw new IllegalArgumentException("차고 보관이 아닌 경우 차고와 슬롯은 저장할 수 없습니다.");
	        }
	    }

	    request.setOwnedId(ownedId);
	    request.setUserId(userId);
	    
	    ownedTransportMapper.updateStorageType(request);
	    
	    // 이미지 변경/삭제 시 기존 파일 삭제
	    String newImageUrl = request.getImageUrl();
	    
	    if (newImageUrl != null && oldImageUrl != null && !oldImageUrl.isEmpty()) {
	    	boolean isRemoved = newImageUrl.isEmpty();
	        boolean isChanged = !newImageUrl.equals(oldImageUrl);

	        if (isRemoved || isChanged) {
	            try {
	                String fileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
	                java.io.File file = new java.io.File(uploadPath, fileName);

	                if (file.exists()) {
	                    file.delete();
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

	@Override
	@Transactional
	public void delete(Long userId, Long ownedId) {
		String imageUrl = ownedTransportMapper.selectImageUrl(ownedId, userId);
		
		if (imageUrl != null && !imageUrl.isEmpty()) {
		    try {
		        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

		        java.io.File file = new java.io.File(uploadPath, fileName);

		        if (file.exists()) {
		            file.delete();
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

	@Override
	@Transactional
	public void swapOwnedTransport(Long userId, SwapOwnedTransportRequest request) {
		if (request == null) {
	        throw new IllegalArgumentException("요청값이 없습니다.");
	    }
		
		Long sourceOwnedId = request.getSourceOwnedId();
		Long targetOwnedId = request.getTargetOwnedId();
		
		if (sourceOwnedId == null || targetOwnedId == null) {
	        throw new IllegalArgumentException("교체 대상 차량 ID가 없습니다.");
	    }

	    if (sourceOwnedId.equals(targetOwnedId)) {
	        throw new IllegalArgumentException("같은 차량끼리는 교체할 수 없습니다.");
	    }
	    
	    OwnedTransportSlotDto source = ownedTransportMapper.selectStorageByOwnedId(sourceOwnedId, userId);
	    OwnedTransportSlotDto target = ownedTransportMapper.selectStorageByOwnedId(targetOwnedId, userId);
	    
	    if (source == null || target == null) {
	        throw new IllegalArgumentException("교체 대상 차량 정보를 찾을 수 없습니다.");
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
}
