package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 보유 이동수단 수정 요청 DTO
 */
@Getter
@Setter
public class OwnedTransportUpdateRequest {
	private Long ownedId;
    private Long userId;
    private Long garageId;
    private Integer slotNo;
    
    /* 보관 상태 */
    private String storageType;
    
    /* 비고 */
    private String remark;
    
    /* 이미지 URL */
    private String imageUrl;
    
    /* 획득 여부 (Y: 획득, N: 미획득) */
    private String acquiredYn;
    
	/* 맨션 위치(PODIUM,D1,D2) */
    private String mansionPosition;
}
