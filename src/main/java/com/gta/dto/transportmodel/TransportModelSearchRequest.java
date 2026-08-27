package com.gta.dto.transportmodel;

import java.util.List;

import lombok.Data;

/**
 * 이동수단 모델 목록 조회 요청 DTO
 */
@Data
public class TransportModelSearchRequest {
	/** 검색어 (이동수단명 등) */
	private String keyword;
	
	/** 제조사 */
    private String manufacturer;

    /** 이동수단 분류 목록 */
    private List<String> categories;

    /** 획득 경로 목록 */
    private List<String> sources;

    /** 개조 가능 장소 목록 */
    private List<String> upgradeLocations;

    /** 특수 기능 목록 */
    private List<String> features;

    /** 페이지 번호 */
    private int page = 1;

    /** 페이지당 조회 개수 */
    private int size = 15;

    /** 정렬 기준 */
    private String sort;
}