package com.gta.dto;

import jakarta.validation.constraints.Min;
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

    @Min(value = 1, message = "slotNo는 1 이상이어야 합니다.")
    private Integer slotNo;
    
    /* 보관 상태 */
    private String storageType;
    
    /* 비고 */
    private String remark;
    
    /* 이미지 URL */
    private String imageUrl;
    
    /* 1차 색상 */
    private String primaryColor;

    /* 2차 색상 */
    private String secondaryColor;

    /* 트림 색상 */
    private String trimColor;

    /* 액센트 색상 */
    private String accentColor;
    
    /* 펄 광택 */
    private String pearlescentColor;

    /* 상징 */
    private String decal;
}
