package com.gta.dto;

import lombok.Data;

/**
 * 보유 이동수단 목록 조회 응답 DTO
 * - owned_transport 기준 + transport_model, garage/slot 정보 조인 결과를 담는다.
 */
@Data
public class OwnedTransportListDto {
	// 보유 이동수단 ID
	private Long ownedId;    
	
	// 이동수단 모델 ID
    private Long modelId;            
    
    // 제조사
    private String manufacturer;

    // 이동수단명
    private String name;

    // 이동수단 카테고리
    private String transportCategory;
    
    // 가격
    private Long price;

    // 출시일
    private String releaseDate;

    // 보유 상태
    private String ownStatus;      
    
    // 차고 ID
    private Long garageId;

    // 차고명
    private String garageName;

    // 차고 슬롯 번호
    private Integer slotNo;

    // 보관 유형
    private String storageType;

    // 사용자 지정 차고명
    private String alias;

    // 차고 설명
    private String description;

    // 차고 접힘 여부
    private String collapsedYn;

    // 비고
    private String remark;

    // 개조 위치
    private String upgradeLocation;
    
    // 랩타임
    private Integer lapTime;

    // 최고속도
    private Double topSpeed;

    // 개인 랩타임
    private Integer personalLapTime;
    
    // 랩타임 전체 순위
    private Integer lapRank;

    // 최고속도 전체 순위
    private Integer speedRank;

    // 랩타임 카테고리 순위
    private Integer lapCategoryRank;

    // 최고속도 카테고리 순위
    private Integer speedCategoryRank;
    
    // 랩타임 전체 이동수단 수
    private Integer lapTotalCount;

    // 랩타임 카테고리 이동수단 수
    private Integer lapCategoryTotalCount;

    // 최고속도 전체 이동수단 수
    private Integer speedTotalCount;

    // 최고속도 카테고리 이동수단 수
    private Integer speedCategoryTotalCount;
    
    // 개인 랩타임 전체 순위
    private Integer personalLapRank;

    // 개인 랩타임 카테고리 순위
    private Integer personalLapCategoryRank;

    // 개인 랩타임 전체 이동수단 수
    private Integer personalLapTotalCount;

    // 개인 랩타임 카테고리 이동수단 수
    private Integer personalLapCategoryTotalCount;
    
    // 획득 경로
    private String source;

    // 특징
    private String features;

    // 이미지 URL
    private String imageUrl;
    
    // 획득 여부 (Y: 획득, N: 미획득)
    private String acquiredYn;
    
    // 맨션 위치 (PODIUM, D1, D2)
    private String mansionPosition;
}
