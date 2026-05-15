package com.gta.dto;

import lombok.Data;

@Data
public class HangarOrderUpdateRequest {
	/** 보유 이동수단 ID */
    private Long ownedId;

    /** 격납층 정렬 순서 */
    private Integer hangarSortOrder;
}
