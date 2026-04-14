package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.GarageListDto;
import com.gta.dto.GarageSettingUpdateRequest;

/**
 * 차고(보관 장소) Mapper
 */
@Mapper
public interface GarageMapper {

	List<GarageListDto> selectGarageList(@Param("userId") Long userId);

	List<Integer> selectOccupiedSlotsByGarageId(@Param("garageId") Long garageId,
											    @Param("userId") Long userId);

	int existsUserGarageSetting(@Param("garageId") Long garageId,
            					@Param("userId") Long userId);

	void insertUserGarageSetting(@Param("garageId") Long garageId,
	             				 @Param("userId") Long userId,
	             				 @Param("req") GarageSettingUpdateRequest request);
	
	void updateUserGarageSetting(@Param("garageId") Long garageId,
	             				 @Param("userId") Long userId,
	             				 @Param("req") GarageSettingUpdateRequest request);

}
