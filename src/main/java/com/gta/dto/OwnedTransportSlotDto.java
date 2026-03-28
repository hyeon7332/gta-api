package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnedTransportSlotDto {
	private Long ownedId;
    private Long garageId;
    private Integer slotNo;
}
