package com.gta.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Value("${upload.owned-transport.path}")
    private String uploadPath;
	
	@Value("${upload.owned-transport.url-prefix}")
    private String urlPrefix;

	@Override
	public String uploadOwnedTransportImage(MultipartFile file) {
		if (file == null || file.isEmpty()) {
            throw new RuntimeException("파일이 없습니다.");
        }

        // 디렉토리 생성
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일명 생성
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString() + ext;

        File target = new File(uploadPath, savedName);

        try {
            file.transferTo(target);
        } catch (IOException e) {
        	throw new RuntimeException("파일 저장 실패", e);
        }

        return urlPrefix + "/" + savedName;
	}

}
