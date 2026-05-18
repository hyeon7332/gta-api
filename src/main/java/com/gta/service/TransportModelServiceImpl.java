package com.gta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gta.dto.TransportModelCreateRequest;
import com.gta.dto.TransportModelDto;
import com.gta.dto.TransportModelSearchRequest;
import com.gta.dto.TransportModelUpdateRequest;
import com.gta.exception.BusinessException;
import com.gta.mapper.TransportModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportModelServiceImpl implements TransportModelService {
	
	private final TransportModelMapper transportModelMapper;

	@Override
	public Map<String, Object> getList(TransportModelSearchRequest request) {
		int offset = (request.getPage() - 1) * request.getSize();
		
		List<TransportModelDto> list = transportModelMapper.selectList(
            request,
            offset,
            request.getSize()
        );
		
		int total = transportModelMapper.selectCount(request);
		
		Map<String, Object> result = new HashMap<>();
        result.put("items", list);
        result.put("total", total);
        result.put("page", request.getPage());
        result.put("size", request.getSize());

	    return result;
	}

	@Override
	public int createTransportModel(TransportModelCreateRequest request) {
		try {
			return transportModelMapper.insertTransportModel(request);
		} catch (DuplicateKeyException  e) {
			throw new BusinessException("이미 등록된 모델입니다.");
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("입력값이 허용 범위를 초과했습니다. 최고속도는 9999.99 이하로 입력해주세요.");
		}
	}

	@Override
	public int updateTransportModel(Long modelId, TransportModelUpdateRequest request) {
		try {
	        return transportModelMapper.updateTransportModel(modelId, request);
	    } catch (DuplicateKeyException e) {
			throw new BusinessException("이미 등록된 모델입니다.");
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("입력값이 허용 범위를 초과했습니다. 최고속도는 9999.99 이하로 입력해주세요.");
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
