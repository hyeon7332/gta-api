package com.gta.dto;

import lombok.Data;

@Data
public class TransportModelDto {
    private Long modelId;
    private String manufacturer;
    private String name;
    private String transportCategory;
    private String upgradeType;
    private String upgradeLocation;
    private Integer lapTime;
    private Double topSpeed;
    private Long price;
    private String releaseDate;
    private String source;
    private Double weight;
    private Integer driveGears;
    private String driveTrain;
    private Integer seats;
    private String performance;
    private String features;
}
