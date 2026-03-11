package com.gta.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gta.dto.GarageListDto;
import com.gta.mapper.GarageMapper;

import lombok.RequiredArgsConstructor;

/**
 * 차고(보관 장소) 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService{
	
	private final GarageMapper garageMapper;

	@Override
	public List<GarageListDto> getList() {
		List<GarageListDto> list = garageMapper.selectGarageList();

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
	}

	@Override
	public List<Integer> getOccupiedSlots(Long garageId) {
		List<Integer> list = garageMapper.selectOccupiedSlotsByGarageId(garageId);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
	}

}
