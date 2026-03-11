package com.gta.service;

import java.util.List;

import com.gta.dto.TransportModelCreateRequest;
import com.gta.dto.TransportModelDto;
import com.gta.dto.TransportModelUpdateRequest;


/**
 * 이동수단 모델(사전) 서비스
 */
public interface TransportModelService {
	
	/**
	 * 이동수단 모델 목록 조회
	 * 
	 * @param keyword 검색어
	 * @return 이동수단 모델 목록
	 */
	List<TransportModelDto> getList(String keyword);
	
	/**
     * 이동수단 모델 등록
     *
     * @param request 등록 요청 DTO
     * @return 등록 건수
     */
    int createTransportModel(TransportModelCreateRequest request);

    /**
     * 이동수단 모델 수정
     *
     * @param modelId 이동수단 모델 ID
     * @param request 수정 요청 DTO
     * @return 수정 건수
     */
    int updateTransportModel(Long modelId, TransportModelUpdateRequest request);

    /**
     * 이동수단 모델 삭제
     *
     * @param modelId 이동수단 모델 ID
     * @return 삭제 건수
     */
    int deleteTransportModel(Long modelId);
}
