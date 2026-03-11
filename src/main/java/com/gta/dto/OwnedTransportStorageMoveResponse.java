package com.gta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 보유 탈것의 보관 부동산(차고/부동산) 변경 응답 DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class OwnedTransportStorageMoveResponse {
	private Long ownedId;
    private Long propertyId;
    private Integer slotNo;
}
