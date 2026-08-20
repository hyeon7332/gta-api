package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.RankingResponse;
import com.gta.dto.RankingSearchRequest;

/**
 * 이동수단 랭킹 Mapper
 */
@Mapper
public interface RankingMapper {

	/**
     * 상위 1~3위 랭킹 조회
     *
     * @param userId 로그인 사용자 ID
     * @param filter 랭킹 조회 조건
     * @return 상위 3개 랭킹 목록
     */
    List<RankingResponse> selectTop3(
            @Param("userId") Long userId,
            @Param("filter") RankingSearchRequest filter
    );
    
    /**
     * 4위 이하 랭킹 페이징 조회
     *
     * @param userId 로그인 사용자 ID
     * @param filter 랭킹 조회 조건
     * @param offset 조회 시작 위치
     * @param size 조회 개수
     * @return 현재 페이지 랭킹 목록
     */
    List<RankingResponse> selectRankingPage(
            @Param("userId") Long userId,
            @Param("filter") RankingSearchRequest filter,
            @Param("offset") int offset,
            @Param("size") int size
    );
    
    /**
     * 4위 이하 랭킹 전체 건수 조회
     *
     * @param userId 로그인 사용자 ID
     * @param filter 랭킹 조회 조건
     * @return 4위 이하 전체 건수
     */
    int selectRankingCount(
            @Param("userId") Long userId,
            @Param("filter") RankingSearchRequest filter
    );
}
