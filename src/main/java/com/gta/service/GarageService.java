package com.gta.service;

import java.util.List;

import com.gta.dto.GarageListDto;

/**
 * 차고(보관 장소) 서비스
 */
public interface GarageService {

	List<GarageListDto> getList();

	List<Integer> getOccupiedSlots(Long garageId);

}
