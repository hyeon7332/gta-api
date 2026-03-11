package com.gta.dto;

import lombok.Data;

/**
 * 보유 이동수단 목록 조회 응답 DTO
 * - owned_transport 기준 + transport_model, garage/slot 정보 조인 결과를 담는다.
 */
@Data
public class OwnedTransportListDto {
	private Long ownedId;            

    private Long modelId;            
    private String manufacturer;     
    private String name;             
    private String transportCategory;
    private String decal;
    private Long price;            
    private String releaseDate;      

    private String ownStatus;        

    private Long garageId;           
    private String garageName;       
    private String garageType;       
    private Integer slotNo;          
}
