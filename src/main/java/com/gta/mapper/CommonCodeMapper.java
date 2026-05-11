package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.CommonCodeDto;


/**
 * 공통 코드 Mapper
 */
@Mapper
public interface CommonCodeMapper {

    /**
     * 그룹코드 기준 공통 코드 목록 조회
     */
    List<CommonCodeDto> selectCommonCodes(@Param("groupCode") String groupCode);
}
