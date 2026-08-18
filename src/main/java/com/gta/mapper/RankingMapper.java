package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.RankingResponse;

/**
 * 이동수단 랭킹 Mapper
 */
@Mapper
public interface RankingMapper {

	/**
     * 이동수단 랭킹 목록 조회
     * 
     * @param userId
     * @param type 랭킹 기준
     * @param category 이동수단 분류
     * @return 랭킹 목록
     */
	List<RankingResponse> selectRanking(
	        @Param("userId") Long userId,
	        @Param("type") String type,
	        @Param("category") String category
	);

}
