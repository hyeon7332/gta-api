package com.gta.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gta.dto.GarageListDto;
import com.gta.dto.GarageSettingUpdateRequest;
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
	public List<GarageListDto> getList(Long userId) {
		List<GarageListDto> list = garageMapper.selectGarageList(userId);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
	}

	@Override
	public List<Integer> getOccupiedSlots(Long garageId, Long userId) {
		List<Integer> list = garageMapper.selectOccupiedSlotsByGarageId(garageId, userId);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
	}

	/**
     * 사용자별 차고 설정 저장 (별칭/설명)
     */
	@Override
	@Transactional
	public void updateGarageSetting(Long garageId, Long userId, GarageSettingUpdateRequest request) {
		// 1. 존재 여부 확인
        int exists = garageMapper.existsUserGarageSetting(garageId, userId);

        // 2. 존재하면 UPDATE
        if (exists > 0) {
            garageMapper.updateUserGarageSetting(garageId, userId, request);
        }
        // 3. 없으면 INSERT
        else {
            garageMapper.insertUserGarageSetting(garageId, userId, request);
        }
	}
}
