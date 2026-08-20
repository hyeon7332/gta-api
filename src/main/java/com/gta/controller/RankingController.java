package com.gta.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.RankingListResponse;
import com.gta.dto.RankingSearchRequest;
import com.gta.service.RankingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 이동수단 랭킹 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rankings")
public class RankingController {
	
	private final RankingService rankingService;
	
	/**
     * 이동수단 랭킹 목록 조회
     * 
     * @param userId 로그인 사용자 ID
     * @param type 랭킹 기준 (LAP_TIME: 랩타임, TOP_SPEED: 최고속도)
     * @param category 이동수단 분류 (미지정 시 전체)
     * @param page 현재 페이지
     * @param size 페이지당 조회 개수
     * @return TOP3 + 4위 이하 페이징 목록
     */
	@GetMapping
	public RankingListResponse getRanking(
			HttpServletRequest request,
			@ModelAttribute RankingSearchRequest filter)
    {
		Long userId = (Long) request.getAttribute("userId");
		
		return rankingService.getRanking(userId, filter);

	}
}
