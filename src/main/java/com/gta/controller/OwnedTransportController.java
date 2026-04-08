package com.gta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportListDto;
import com.gta.dto.OwnedTransportUpdateRequest;
import com.gta.dto.SwapOwnedTransportRequest;
import com.gta.service.OwnedTransportService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owned-transports")
public class OwnedTransportController {
	
	private final OwnedTransportService ownedTransportService;
    
    @GetMapping
    public List<OwnedTransportListDto> getList(HttpServletRequest request)
    {
    	Long userId = (Long) request.getAttribute("userId");
    	return ownedTransportService.getList(userId);
    }
    
    @PostMapping
    public ResponseEntity<Integer> create(HttpServletRequest request,
    									  @RequestBody OwnedTransportCreateRequest req)
    {
    	Long userId = (Long) request.getAttribute("userId");
    	int inserted = ownedTransportService.create(userId, req);
        return ResponseEntity.ok(inserted);
    }
    
    @PatchMapping("/{ownedId}")
    public void update(HttpServletRequest request,
    				   @PathVariable Long ownedId,
                       @RequestBody @Valid OwnedTransportUpdateRequest requestBody)
    {
    	Long userId = (Long) request.getAttribute("userId");
    	ownedTransportService.update(userId, ownedId, requestBody);
    }
    
    @DeleteMapping("/{ownedId}")
    public ResponseEntity<Void> delete(HttpServletRequest request,
    								   @PathVariable Long ownedId)
    {
    	Long userId = (Long) request.getAttribute("userId");
    	ownedTransportService.delete(userId, ownedId);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/swap")
    public ResponseEntity<Void> swap(HttpServletRequest request,
    								 @RequestBody SwapOwnedTransportRequest requestBody) {
    	Long userId = (Long) request.getAttribute("userId");
    	ownedTransportService.swapOwnedTransport(userId, requestBody);
    	return ResponseEntity.ok().build();
    }
}
