package com.gta.dto;

import java.util.List;

import lombok.Data;

@Data
public class TransportModelSearchRequest {
	private String keyword;
    private String manufacturer;
    private String category;
    private String source;
    
    private List<String> upgradeLocations;
    private List<String> features;

    private int page = 1;
    private int size = 15;
    private String sort;
}