package com.gta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.TransportModelCreateRequest;
import com.gta.dto.TransportModelDto;
import com.gta.dto.TransportModelUpdateRequest;
import com.gta.service.TransportModelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 이동수단 모델 목록 조회 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transport-models")
public class TransportModelController {
	private final TransportModelService transportModelService;

	/**
     * 이동수단 모델 목록 조회
     *
     * @param keyword 검색어
     * @return 이동수단 모델 목록
     */
	@GetMapping
	public List<TransportModelDto> getList(@RequestParam(required = false) String keyword) {
		return transportModelService.getList(keyword);
	}
	
	/**
     * 이동수단 모델 등록
     *
     * @param request 등록 요청 DTO
     * @return 등록 건수
     */
    @org.springframework.web.bind.annotation.PostMapping
    public ResponseEntity<Integer> createTransportModel(@Valid @RequestBody TransportModelCreateRequest request) {
        int inserted = transportModelService.createTransportModel(request);
        return ResponseEntity.ok(inserted);
    }

    /**
     * 이동수단 모델 수정
     *
     * @param modelId 이동수단 모델 ID
     * @param request 수정 요청 DTO
     * @return 수정 건수
     */
    @org.springframework.web.bind.annotation.PutMapping("/{modelId}")
    public ResponseEntity<Integer> updateTransportModel(@PathVariable Long modelId,
                                                        @Valid @RequestBody TransportModelUpdateRequest request) {
        int updated = transportModelService.updateTransportModel(modelId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * 이동수단 모델 삭제
     *
     * @param modelId 이동수단 모델 ID
     * @return 삭제 건수
     */
    @DeleteMapping("/{modelId}")
    public ResponseEntity<Integer> deleteTransportModel(@PathVariable Long modelId) {
        int deleted = transportModelService.deleteTransportModel(modelId);
        return ResponseEntity.ok(deleted);
    }
 
}
