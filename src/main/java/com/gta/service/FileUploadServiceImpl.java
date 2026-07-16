package com.gta.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

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
        	// 원본 저장
            file.transferTo(target);
            
            // 썸네일 생성
            String thumbName = savedName.substring(0, savedName.lastIndexOf('.'))
            		+ "_thumb"
            		+ ext;
            
            File thumFile = new File(uploadPath, thumbName);
            
            Thumbnails.of(target)
            		  .size(400, 228)
            		  .keepAspectRatio(true)
            		  .toFile(thumFile);
            
        } catch (IOException e) {
        	throw new RuntimeException("파일 저장 실패", e);
        }

        return urlPrefix + "/" + savedName;
	}

}
