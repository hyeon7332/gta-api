package com.gta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gta.dto.CommonCodeDto;
import com.gta.mapper.CommonCodeMapper;

import lombok.RequiredArgsConstructor;

/**
 * 공통 코드 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeMapper commonCodeMapper;

    /**
     * 그룹코드 기준 공통 코드 목록 조회
     */
    @Override
    public List<CommonCodeDto> selectCommonCodes(String groupCode) {
        return commonCodeMapper.selectCommonCodes(groupCode);
    }
}
