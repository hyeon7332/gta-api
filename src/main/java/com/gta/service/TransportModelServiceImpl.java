package com.gta.service;

import java.util.List;

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
	public List<TransportModelDto> getList(String keyword) {
		return transportModelMapper.selectTransportModelList(keyword);
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
