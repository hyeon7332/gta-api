package com.gta.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gta.service.FileUploadService;

import lombok.RequiredArgsConstructor;

/**
 * 파일 업로드 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/uploads")
public class FileUploadController {
	
	private final FileUploadService fileUploadService;

	@PostMapping("/owned-transport")
	public ResponseEntity<Map<String, String>> uploadOwnedTransportImage(@RequestParam("file") MultipartFile file) {
		String imageUrl = fileUploadService.uploadOwnedTransportImage(file);

		return ResponseEntity.ok(Map.of(
                "imageUrl", imageUrl 
        ));
	}
}
