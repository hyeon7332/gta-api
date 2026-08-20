package com.gta.service;

import com.gta.dto.RankingListResponse;
import com.gta.dto.RankingSearchRequest;

/**
 * 이동수단 랭킹 서비스
 */
public interface RankingService {

    /**
     * 이동수단 랭킹 목록 조회
     *
     * @param userId 로그인 사용자 ID
     * @param filter 랭킹 조회 조건
     * @return 이동수단 랭킹 목록
     */
	RankingListResponse getRanking(Long userId, RankingSearchRequest filter);
}
