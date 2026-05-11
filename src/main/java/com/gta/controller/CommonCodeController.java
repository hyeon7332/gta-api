package com.gta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.CommonCodeDto;
import com.gta.service.CommonCodeService;

import lombok.RequiredArgsConstructor;


/**
 * 공통 코드 조회 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    /**
     * 그룹코드 기준 공통 코드 목록 조회
     */
    @GetMapping("/api/common-codes")
    public List<CommonCodeDto> selectCommonCodes(@RequestParam String groupCode) {
        return commonCodeService.selectCommonCodes(groupCode);
    }
}
