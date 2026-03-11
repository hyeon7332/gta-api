package com.gta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.GarageListDto;
import com.gta.service.GarageService;

import lombok.RequiredArgsConstructor;

/**
 * 차고(보관 장소) 조회 컨트롤러
 *
 * - 차고 목록 조회 API 제공
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garages")
public class GarageController {
	
	private final GarageService garageService;

    /**
     * 차고 목록 조회
     *
     * @return 차고 목록
     */
    @GetMapping
    public List<GarageListDto> getList()
    {
        return garageService.getList();
    }
    
    /**
     * 특정 차고에서 사용 중인 슬롯 번호 목록 조회
     *
     * @param garageId 차고 ID
     * @return 사용 중 슬롯 번호 목록 (예: [3, 7, 12])
     */
    @GetMapping("/{garageId}/occupied-slots")
    public List<Integer> getOccupiedSlots(@PathVariable Long garageId)
    {
        return garageService.getOccupiedSlots(garageId);
    }
}
