package com.gta.service;

import java.util.List;

import com.gta.dto.RankingResponse;

/**
 * 이동수단 랭킹 서비스
 */
public interface RankingService {

	/**
	 * 이동수단 랭킹 조회
	 * 
	 * @param userId
     * @param type 랭킹 기준
     *             - LAP_TIME : 랩타임
     *             - TOP_SPEED : 최고속도
     * @param category 이동수단 분류
     *                 - null 또는 빈 값이면 전체 분류 조회
     * @return 이동수단 랭킹 목록
	 */
	List<RankingResponse> getRanking(
	        Long userId,
	        String type,
	        String category
	);
}
