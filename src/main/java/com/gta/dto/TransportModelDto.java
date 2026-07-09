package com.gta.dto;

import lombok.Data;

@Data
public class TransportModelDto {
    private Long modelId;
    private String manufacturer;
    private String name;
    private String transportCategory;
    private String upgradeLocation;
    private Integer lapTime;
    private Double topSpeed;
    private Long price;
    private String releaseDate;
    private String source;
    private String features;
    private Integer ownedCount;
}
