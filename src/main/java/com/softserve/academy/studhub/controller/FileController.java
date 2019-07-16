package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @PreAuthorize("permitAll()")
    ResponseEntity<String> uploadFile(@RequestParam MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(multipartFile));
    }
}
