package com.gta.dto;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 이동수단 랭킹 조회 응답 DTO
 * - 랩타임 / 개인 측정 랩타임 / 최고속도 랭킹에서 공통으로 사용
 * - 이동수단 모델 정보와 사용자의 보유 이동수단 정보를 함께 반환
 */
@Data
public class RankingResponse {
	// 이동수단 모델 고유 ID
    private Long modelId;

    // 제조사
    private String manufacturer;

    // 이동수단 모델명
    private String name;

    // 이동수단 분류
    private String transportCategory;

    // 랩타임 (밀리초)
    private Integer lapTime;

    // 최고속도
    private BigDecimal topSpeed;

    // 개인 측정 랩타임
    private Integer personalLapTime;
    
    // 보유 이동수단 대표 이미지 URL
    private String imageUrl;

    // 보유 이동수단 고유 ID
    private Long ownedId;

    // 보관 중인 차고명
    private String garageName;
    
    // 이동수단 특징
    private String features;
}
