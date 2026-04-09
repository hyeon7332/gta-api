package com.gta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
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
	public Map<String, Object> getList(String keyword, int page, int size, String sort) {
		int offset = (page - 1) * size;

	    List<TransportModelDto> list =
	    		transportModelMapper.selectList(keyword, offset, size, sort);

	    int total =
	            transportModelMapper.selectCount(keyword);

	    Map<String, Object> result = new HashMap<>();

	    result.put("items", list);
	    result.put("total", total);

	    return result;
	}

	@Override
	public int createTransportModel(TransportModelCreateRequest request) {
		try {
			return transportModelMapper.insertTransportModel(request);
		} catch (DuplicateKeyException  e) {
			throw new IllegalArgumentException("이미 등록된 모델입니다.");
		}
	}

	@Override
	public int updateTransportModel(Long modelId, TransportModelUpdateRequest request) {
		try {
	        return transportModelMapper.updateTransportModel(modelId, request);
	    } catch (DuplicateKeyException e) {
	        throw new IllegalArgumentException("이미 등록된 모델입니다.");
	    }
	}

	@Override
	public int deleteTransportModel(Long modelId) {
		return transportModelMapper.deleteTransportModel(modelId);
	}

	@Override
	public List<TransportModelDto> getOptions() {
		return transportModelMapper.selectOptions();
	}
}
