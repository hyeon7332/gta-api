package com.gta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gta.dto.RankingResponse;
import com.gta.mapper.RankingMapper;

import lombok.RequiredArgsConstructor;

/**
 * 이동수단 랭킹 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
	
	private final RankingMapper rankingMapper;

	@Override
	public List<RankingResponse> getRanking(Long userId, String type, String category) {
		// 랭킹 기준값 검증
        if (!"LAP_TIME".equals(type) && !"TOP_SPEED".equals(type))
        {
            throw new IllegalArgumentException("지원하지 않는 랭킹 기준입니다.");
        }
        
        // 전체 선택 시 빈 문자열을 null로 정리
        if (category != null && category.isBlank())
        {
            category = null;
        }
		
        return rankingMapper.selectRanking(userId, type, category);
	}
}
