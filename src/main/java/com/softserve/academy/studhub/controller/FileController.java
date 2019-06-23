package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

	private final FileService fileService;


	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping(value = "/api/files")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		fileService.storeFile(file);
	}

}
