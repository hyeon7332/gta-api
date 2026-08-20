package com.gta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gta.dto.RankingListResponse;
import com.gta.dto.RankingResponse;
import com.gta.dto.RankingSearchRequest;
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
	public RankingListResponse getRanking(Long userId, RankingSearchRequest filter) {
		// 랭킹 기준값 검증
        if (!"LAP_TIME".equals(filter.getType())
                && !"TOP_SPEED".equals(filter.getType()))
        {
            throw new IllegalArgumentException("지원하지 않는 랭킹 기준입니다.");
        }
        
        // 전체 선택 시 빈 문자열을 null로 정리
        if (filter.getCategory() != null
                && filter.getCategory().isBlank())
        {
            filter.setCategory(null);
        }
        
        // 페이지 기본값 보정
        if (filter.getPage() < 1)
        {
            filter.setPage(1);
        }
        
        // 페이지당 조회 개수 기본값 보정
        if (filter.getSize() < 1)
        {
            filter.setSize(25);
        }

        // 1~3위 조회
        List<RankingResponse> top3 =
                rankingMapper.selectTop3(userId, filter);

        // 4위 이하 전체 건수
        int totalCount =
                rankingMapper.selectRankingCount(userId, filter);

        // 4위 이하 페이징 OFFSET
        int offset =
        		3 + (filter.getPage() - 1) * filter.getSize();

        // 현재 페이지의 4위 이하 목록 조회
        List<RankingResponse> items =
                rankingMapper.selectRankingPage(
                        userId,
                        filter,
                        offset,
                        filter.getSize()
                );
        
        // 응답 생성
        RankingListResponse response = new RankingListResponse();

        response.setTop3(top3);
        response.setItems(items);
        response.setTotalCount(totalCount);
        response.setPage(filter.getPage());
        response.setSize(filter.getSize());

        return response;
	}
}
