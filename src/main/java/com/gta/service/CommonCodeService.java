package com.gta.service;

import java.util.List;

import com.gta.dto.CommonCodeDto;

/**
 * 공통 코드 서비스
 */
public interface CommonCodeService {

    /**
     * 그룹코드 기준 공통 코드 목록 조회
     */
    List<CommonCodeDto> selectCommonCodes(String groupCode);
}
