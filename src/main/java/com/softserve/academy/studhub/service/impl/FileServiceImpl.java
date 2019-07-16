package com.softserve.academy.studhub.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.softserve.academy.studhub.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileServiceImpl implements FileService {

	private final static Map<Object, Object> CONFIG = new HashMap<>();

	static {
		CONFIG.put("cloud_name", "studhubcloud");
		CONFIG.put("api_key", "728188731214736");
		CONFIG.put("api_secret", "8d9xEPsgdsDVGy71nWQxHZggOow");
	}

	private Cloudinary cloudinary = new Cloudinary(CONFIG);

	public String uploadFile(MultipartFile toUpload) throws IOException {

		@SuppressWarnings("rawtypes")
		Map uploadResult = cloudinary.uploader().upload(toUpload.getBytes(),
			ObjectUtils.asMap("use_filename", "true", "unique_filename", "true"));
		return (String) uploadResult.get("url");
	}
}
