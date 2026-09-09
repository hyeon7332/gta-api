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
	 * @param request 로그인 사용자 정보를 확인하기 위한 HTTP 요청
	 * @param filter 랭킹 검색 조건
	 * @return TOP3 + 4위 이하 페이징 목록
	 */
	@GetMapping
	public RankingListResponse getRanking(HttpServletRequest request,
										  @ModelAttribute RankingSearchRequest filter)
    {
		Long userId = (Long) request.getAttribute("userId");
		
		return rankingService.getRanking(userId, filter);

	}
}
