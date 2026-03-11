package com.gta.dto;

import lombok.Data;

/**
 * 보유 이동수단 등록 요청 DTO
 */
@Data
public class OwnedTransportCreateRequest {
	/** 보유 이동수단 고유 ID (insert 후 생성키 받기용) */
    private Long ownedId;

    /** 이동수단 모델 ID */
    private Long modelId;
    
    /** 상징 */
    private String decal;

    /** 보유 상태 (기본: 보유) */
    private String ownStatus;

    /** 차고 ID (선택) */
    private Long garageId;

    /** 슬롯 번호 (차고 선택 시 필수) */
    private Integer slotNo;
}
