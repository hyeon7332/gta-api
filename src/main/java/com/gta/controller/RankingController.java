package com.gta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.RankingResponse;
import com.gta.service.RankingService;

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
     * @param type 랭킹 기준 (LAP_TIME: 랩타임, TOP_SPEED: 최고속도)
     * @param category 이동수단 분류 (미지정 시 전체)
     * @return 이동수단 랭킹 목록
     */
	@GetMapping
	public List<RankingResponse> getRanking(
            @RequestAttribute Long userId,
            @RequestParam String type,
            @RequestParam(required = false) String category)
    {
		return rankingService.getRanking(userId, type, category);
	}
}
