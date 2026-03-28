package com.gta.service;

import java.util.List;

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
	
	@Override
	public List<OwnedTransportListDto> getList()
	{
	    return ownedTransportMapper.selectList();
	}

	@Override
	public int create(OwnedTransportCreateRequest req) {
		// 기본값 처리
	    if (req.getOwnStatus() == null || req.getOwnStatus().trim().isEmpty()) {
	        req.setOwnStatus("보유");
	    }
	    
	    // 차고/슬롯 검증
	    if (req.getGarageId() == null) {
	        if (req.getSlotNo() != null) {
	            throw new IllegalArgumentException("차고를 선택하지 않으면 슬롯을 선택할 수 없습니다.");
	        }
	    } else {
	        if (req.getSlotNo() == null) {
	            throw new IllegalArgumentException("차고를 선택한 경우 슬롯은 필수입니다.");
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

	    // 차고 + 슬롯이 모두 선택된 경우만 storage insert
	    if (req.getGarageId() != null && req.getSlotNo() != null) {
	    	ownedTransportMapper.insertOwnedTransportStorage(req);
	    }

	    return inserted;
	}

	@Override
	@Transactional
	public void update(Long ownedId, OwnedTransportUpdateRequest request) {
		if (request.getDecal() != null) {
		    ownedTransportMapper.updateDecal(ownedId, request.getDecal());
		}

		if (request.getGarageId() == null && request.getSlotNo() == null) {
		    return;
		}

		if (request.getGarageId() == null || request.getSlotNo() == null) {
		    throw new IllegalArgumentException("차고와 슬롯은 함께 보내야 합니다.");
		}

	    int exists = ownedTransportMapper.existsByOwnedId(ownedId);

	    Long occupiedOwnedId = ownedTransportMapper.selectOwnedIdByGarageAndSlot(
	        request.getGarageId(),
	        request.getSlotNo()
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
	            request.getSlotNo()
	        );

	        if (inserted == 0) {
	            throw new IllegalStateException("보관 위치 등록에 실패했습니다. ownedId=" + ownedId);
	        }

	        return;
	    }

	    int updated = ownedTransportMapper.updateLocation(
	        ownedId,
	        request.getGarageId(),
	        request.getSlotNo()
	    );

	    if (updated == 0) {
	        throw new IllegalStateException("이동 처리에 실패했습니다. ownedId=" + ownedId);
	    }
	}

	@Override
	@Transactional
	public void delete(Long ownedId) {
		int deleted = ownedTransportMapper.deleteById(ownedId);
		
		if (deleted == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제 대상이 없습니다.");
		}
	}

	@Override
	@Transactional
	public void swapOwnedTransport(SwapOwnedTransportRequest request) {
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
	    
	    OwnedTransportSlotDto source = ownedTransportMapper.selectStorageByOwnedId(sourceOwnedId);
	    OwnedTransportSlotDto target = ownedTransportMapper.selectStorageByOwnedId(targetOwnedId);
	    
	    if (source == null || target == null) {
	        throw new IllegalArgumentException("교체 대상 차량 정보를 찾을 수 없습니다.");
	    }
	    
	    ownedTransportMapper.updateStorageSlotByOwnedId(
    	    sourceOwnedId,
    	    source.getGarageId(),
    	    -1
    	);

    	ownedTransportMapper.updateStorageSlotByOwnedId(
    	    targetOwnedId,
    	    source.getGarageId(),
    	    source.getSlotNo()
    	);

    	ownedTransportMapper.updateStorageSlotByOwnedId(
    	    sourceOwnedId,
    	    target.getGarageId(),
    	    target.getSlotNo()
    	);
	}
}
