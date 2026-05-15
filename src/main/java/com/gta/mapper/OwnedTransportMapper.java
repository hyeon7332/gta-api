package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.HangarOrderUpdateRequest;
import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportSlotDto;
import com.gta.dto.OwnedTransportUpdateRequest;

@Mapper
public interface OwnedTransportMapper {
	/* 목록 조회 */
	List<OwnedTransportListDto> selectList(@Param("userId") Long userId);

	/* 개수 조회 */
	int selectOwnedTransportCount(@Param("userId") Long userId);

    int insertOwnedTransport(OwnedTransportCreateRequest req);

	/* 삭제 */
    int deleteById(@Param("ownedId") Long ownedId,
            	   @Param("userId") Long userId);

	/* 위치 존재 여부 */
    int existsByOwnedId(@Param("ownedId") Long ownedId,
            			@Param("userId") Long userId);

	/* 슬롯 점유 차량 조회 */
    Long selectOwnedIdByGarageAndSlot(@Param("garageId") Long garageId,
            						  @Param("slotNo") Integer slotNo,
            						  @Param("userId") Long userId);

	/* 위치 수정 */
    int updateLocation(@Param("ownedId") Long ownedId,
			           @Param("garageId") Long garageId,
			           @Param("slotNo") Integer slotNo,
			           @Param("userId") Long userId);

	/* 위치 등록 */
    int insertLocation(@Param("ownedId") Long ownedId,
            		   @Param("garageId") Long garageId,
            		   @Param("slotNo") Integer slotNo,
            		   @Param("userId") Long userId);

	/* storage 삭제 */
    int deleteByOwnedId(@Param("ownedId") Long ownedId,
            			@Param("userId") Long userId);

    int insertOwnedTransportStorage(OwnedTransportCreateRequest req);

	/* storage 단건 조회 */
    OwnedTransportSlotDto selectStorageByOwnedId(@Param("ownedId") Long ownedId,
            									 @Param("userId") Long userId);
	
	/* storage 슬롯 변경 */
    int updateStorageSlotByOwnedId(@Param("ownedId") Long ownedId,
						           @Param("garageId") Long garageId,
						           @Param("slotNo") Integer slotNo,
						           @Param("userId") Long userId);

    int updateStorageType(OwnedTransportUpdateRequest req);

    String selectImageUrl(@Param("ownedId") Long ownedId,
            			  @Param("userId") Long userId);
    
    /* 모델 ID 기준 features 조회 */
    String selectFeaturesByModelId(@Param("modelId") Long modelId);

    /* 보유 이동수단 ID 기준 features 조회 */
    String selectFeaturesByOwnedId(@Param("ownedId") Long ownedId);
    
    /* 보관 타입별 개수 조회 */
    int countByStorageType(@Param("userId") Long userId,
                           @Param("storageType") String storageType);
    
    /* 격납층 정렬 순서 수정 */
    int updateHangarOrder(@Param("userId") Long userId,
                          @Param("request") HangarOrderUpdateRequest request);
}
