package com.gta.service;

import java.util.Map;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportUpdateRequest;

public interface OwnedTransportService {
	
	Map<String, Object> getList(int page, int size);
	
	int create(OwnedTransportCreateRequest req);

	void update(Long ownedId, OwnedTransportUpdateRequest request);

	void delete(Long ownedId);

}
