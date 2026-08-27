package com.gta.dto.transportmodel;

import lombok.Data;

/**
 * 이동수단 모델 정보 조회 DTO
 */
@Data
public class TransportModelDto {
	// 이동수단 모델 ID
    private Long modelId;

    // 제조사
    private String manufacturer;

    // 이동수단명
    private String name;

    // 이동수단 분류
    private String transportCategory;

    // 개조 위치
    private String upgradeLocation;

    // 랩타임
    private Integer lapTime;

    // 최고속도
    private Double topSpeed;
    
    // 개인 측정 랩타임
    private Integer personalLapTime;

    // 가격
    private Long price;

    // 출시일
    private String releaseDate;

    // 획득 경로
    private String source;

    // 기능
    private String features;

    // 보유 수량
    private Integer ownedCount;
}
