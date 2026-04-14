package com.gta.service;

import java.util.List;

import com.gta.dto.GarageListDto;
import com.gta.dto.GarageSettingUpdateRequest;

/**
 * 차고(보관 장소) 서비스
 */
public interface GarageService {

	List<GarageListDto> getList(Long userId);

	List<Integer> getOccupiedSlots(Long garageId, Long userId);

	void updateGarageSetting(Long garageId, Long userId, GarageSettingUpdateRequest request);

}
