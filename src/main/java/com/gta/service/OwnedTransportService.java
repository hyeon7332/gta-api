package com.gta.service;

import java.util.List;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportUpdateRequest;

public interface OwnedTransportService {
	
	List<OwnedTransportListDto> getList();
	
	int create(OwnedTransportCreateRequest req);

	void update(Long ownedId, OwnedTransportUpdateRequest request);

	void delete(Long ownedId);


}
