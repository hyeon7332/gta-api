package com.gta.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 차고(보관 장소) 목록 조회 DTO
 */
@Getter
@Setter
@ToString
public class GarageListDto {
	private Long garageId;    
    private String name;      
    private Integer capacity; 
    private Integer sortOrder;
    private String alias;
    private String description;
    private String collapsedYn;
}
