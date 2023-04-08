package com.chintan.musicarchivebackend.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;


@Service
public class FileService {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	public String getUploadPath() {
		return uploadPath;
	}

	@PostConstruct
	public void init() {
		try {
			System.out.println("File upload path :" + uploadPath);
			Files.createDirectories(Paths.get(uploadPath));
		} catch(Exception e) {
			throw new RuntimeException("Could not create upload folder!! Error: " + e.getMessage());
		}
	}
	
	public void save(MultipartFile file) {
		try {
			Path root= Paths.get(uploadPath);
			if (!Files.exists(root)) {
				init();
			}
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
		} catch(Exception e) {
			throw new RuntimeException("Could not store file!! Error: " + e.getMessage());
		}
	}
}
