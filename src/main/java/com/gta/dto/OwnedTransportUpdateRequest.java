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
	private String decal;

    private Long garageId;

    @Min(value = 1, message = "slotNo는 1 이상이어야 합니다.")
    private Integer slotNo;
}
