package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.GarageListDto;

/**
 * 차고(보관 장소) Mapper
 */
@Mapper
public interface GarageMapper {

	List<GarageListDto> selectGarageList();

	List<Integer> selectOccupiedSlotsByGarageId(@Param("garageId") Long garageId);

}
