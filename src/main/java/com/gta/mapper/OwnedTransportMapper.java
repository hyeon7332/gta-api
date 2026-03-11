package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;

@Mapper
public interface OwnedTransportMapper {
    List<OwnedTransportListDto> selectOwnedTransportPage(@Param("offset") int offset,
            											 @Param("size") int size);

    int selectOwnedTransportCount();

    int insertOwnedTransport(OwnedTransportCreateRequest req);

    int deleteById(@Param("ownedId") Long ownedId);

    int updateDecal(@Param("ownedId") Long ownedId, @Param("decal") String decal);

    int existsByOwnedId(@Param("ownedId") Long ownedId);

    Long selectOwnedIdByGarageAndSlot(@Param("garageId") Long garageId,
                                      @Param("slotNo") Integer slotNo);

    int updateLocation(@Param("ownedId") Long ownedId,
                       @Param("garageId") Long garageId,
                       @Param("slotNo") Integer slotNo);

    int insertLocation(@Param("ownedId") Long ownedId,
                       @Param("garageId") Long garageId,
                       @Param("slotNo") Integer slotNo);

    int deleteByOwnedId(@Param("ownedId") Long ownedId);

    int insertOwnedTransportStorage(OwnedTransportCreateRequest req);
}
