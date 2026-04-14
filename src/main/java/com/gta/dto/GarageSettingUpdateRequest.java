package com.gta.dto;

import lombok.Data;

/**
 * 사용자별 차고 설정 수정 요청 DTO
 */
@Data
public class GarageSettingUpdateRequest {
	/** 차고 별칭 */
    private String alias;

    /** 차고 설명 */
    private String description;
}
