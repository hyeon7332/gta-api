package com.gta.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.OwnedTransportCreateRequest;
import com.gta.dto.OwnedTransportUpdateRequest;
import com.gta.service.OwnedTransportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owned-transports")
public class OwnedTransportController {
	
	private final OwnedTransportService ownedTransportService;
    
    @GetMapping
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
            						   @RequestParam(defaultValue = "20") int size)
    {
        return ownedTransportService.getList(page, size);
    }
    
    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody OwnedTransportCreateRequest req)
    {
        int inserted = ownedTransportService.create(req);
        return ResponseEntity.ok(inserted);
    }
    
    @PatchMapping("/{ownedId}")
    public void update(@PathVariable Long ownedId,
                       @RequestBody @Valid OwnedTransportUpdateRequest request)
    {
        ownedTransportService.update(ownedId, request);
    }
    
    @DeleteMapping("/{ownedId}")
    public ResponseEntity<Void> delete(@PathVariable Long ownedId)
    {
        ownedTransportService.delete(ownedId);
        return ResponseEntity.ok().build();
    }
}
