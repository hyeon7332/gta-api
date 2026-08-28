package com.gta.dto;

import java.util.List;

import lombok.Data;

/**
 * 이동수단 랭킹 조회 조건 DTO
 */
@Data
public class RankingSearchRequest {
	
    // 랭킹 기준
    private String type;

    // 이동수단 분류
    private List<String> categoryList;

    //  현재 페이지
    private int page = 1;

    // 페이지당 조회 개수
    private int size = 25;
    
    // TOP3 조회 여부
    private boolean includeTop3 = true;
}
