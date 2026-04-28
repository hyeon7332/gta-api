package com.gta.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	String uploadOwnedTransportImage(MultipartFile file);
}
