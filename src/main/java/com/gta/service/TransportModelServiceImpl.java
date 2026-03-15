package com.gta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gta.dto.TransportModelCreateRequest;
import com.gta.dto.TransportModelDto;
import com.gta.dto.TransportModelUpdateRequest;
import com.gta.mapper.TransportModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportModelServiceImpl implements TransportModelService {
	
	private final TransportModelMapper transportModelMapper;

	@Override
	public Map<String, Object> getList(String keyword, int page, int size) {
		int offset = (page - 1) * size;

	    List<TransportModelDto> list =
	            transportModelMapper.selectList(keyword, offset, size);

	    int total =
	            transportModelMapper.selectCount(keyword);

	    Map<String, Object> result = new HashMap<>();

	    result.put("items", list);
	    result.put("total", total);

	    return result;
	}

	@Override
	public int createTransportModel(TransportModelCreateRequest request) {
		return transportModelMapper.insertTransportModel(request);
	}

	@Override
	public int updateTransportModel(Long modelId, TransportModelUpdateRequest request) {
		return transportModelMapper.updateTransportModel(modelId, request);
	}

	@Override
	public int deleteTransportModel(Long modelId) {
		return transportModelMapper.deleteTransportModel(modelId);
	}
}
