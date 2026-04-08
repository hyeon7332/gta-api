package com.gta.service;

import java.util.List;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportUpdateRequest;
import com.gta.dto.SwapOwnedTransportRequest;

public interface OwnedTransportService {
	
	List<OwnedTransportListDto> getList(Long userId);
	
	int create(Long userId, OwnedTransportCreateRequest req);

	void update(Long userId, Long ownedId, OwnedTransportUpdateRequest request);

	void delete(Long userId, Long ownedId);

	void swapOwnedTransport(Long userId, SwapOwnedTransportRequest request);

}
