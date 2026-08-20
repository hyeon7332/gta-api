package com.gta.dto;

import java.util.List;

import lombok.Data;

/**
 * 이동수단 랭킹 목록 응답 DTO
 */
@Data
public class RankingListResponse {
	// 상위 1~3위 목록
    private List<RankingResponse> top3;

    // 4위 이하 현재 페이지 목록
    private List<RankingResponse> items;

    // 4위 이하 전체 건수
    private int totalCount;

    // 현재 페이지
    private int page;

    // 페이지당 조회 개수
    private int size;
}
