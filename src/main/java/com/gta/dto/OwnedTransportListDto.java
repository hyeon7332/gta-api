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
    private Long price;            
    private String releaseDate;      
    private String ownStatus;        
    private Long garageId;           
    private String garageName;       
    private Integer slotNo;     
    private String upgradeType;
    private String storageType;
    private String alias;
    private String description;
    private String collapsedYn;
    private String remark;
    private String upgradeLocation;
    private Integer lapTime;
    private Double topSpeed;
    private Integer lapRank;
    private Integer speedRank;
    private Integer lapCategoryRank;
    private Integer speedCategoryRank;
    private Integer lapTotalCount;
    private Integer lapCategoryTotalCount;
    private Integer speedTotalCount;
    private Integer speedCategoryTotalCount;
    private String source;
    private Double weight;
    private String driveTrain;
    private Integer seats;
    private String features;
    private String imageUrl;
    private String primaryColor;
    private String secondaryColor;
    private String trimColor;
    private String accentColor;
    private String pearlescentColor;
    private String decal;
}
