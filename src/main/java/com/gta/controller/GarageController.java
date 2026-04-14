package com.gta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.GarageListDto;
import com.gta.dto.GarageSettingUpdateRequest;
import com.gta.service.GarageService;

import jakarta.servlet.http.HttpServletRequest;
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
	public List<GarageListDto> getList(HttpServletRequest request) {
	    Long userId = (Long) request.getAttribute("userId");

	    return garageService.getList(userId);
	}
    
    /**
     * 특정 차고에서 사용 중인 슬롯 번호 목록 조회
     *
     * @param garageId 차고 ID
     * @return 사용 중 슬롯 번호 목록 (예: [3, 7, 12])
     */
    @GetMapping("/{garageId}/occupied-slots")
    public List<Integer> getOccupiedSlots(@PathVariable Long garageId,
    									  HttpServletRequest request) {
    	Long userId = (Long) request.getAttribute("userId");
    	
    	return garageService.getOccupiedSlots(garageId, userId);
    }
    
    /**
     * 사용자별 차고 설정 저장
     *
     * @param garageId 차고 ID
     * @param request 차고 설정 요청
     * @param requestHttp 사용자 식별용 요청 객체
     * @return 저장 결과
     */
    @PutMapping("/{garageId}/setting")
    public ResponseEntity<Void> updateGarageSetting(@PathVariable Long garageId,
                                                    @RequestBody GarageSettingUpdateRequest request,
                                                    HttpServletRequest requestHttp)
    {
        Long userId = (Long) requestHttp.getAttribute("userId");

        garageService.updateGarageSetting(garageId, userId, request);

        return ResponseEntity.ok().build();
    }
}
