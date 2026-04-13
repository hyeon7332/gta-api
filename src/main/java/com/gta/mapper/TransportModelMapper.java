package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gta.dto.TransportModelCreateRequest;
import com.gta.dto.TransportModelDto;
import com.gta.dto.TransportModelSearchRequest;
import com.gta.dto.TransportModelUpdateRequest;

@Mapper
public interface TransportModelMapper {

    List<TransportModelDto> selectList(@Param("request") TransportModelSearchRequest request,
                                       @Param("offset") int offset,
                                       @Param("size") int size);

    int selectCount(@Param("request") TransportModelSearchRequest request);

    int insertTransportModel(TransportModelCreateRequest request);

    int updateTransportModel(@Param("modelId") Long modelId,
                             @Param("request") TransportModelUpdateRequest request);

    int deleteTransportModel(@Param("modelId") Long modelId);

    List<TransportModelDto> selectOptions();
}
