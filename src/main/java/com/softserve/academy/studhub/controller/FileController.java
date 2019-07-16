package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.dto.UrlDTO;
import com.softserve.academy.studhub.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @PreAuthorize("permitAll()")
    ResponseEntity<UrlDTO> uploadFile(@RequestParam MultipartFile multipartFile) throws IOException {

        UrlDTO result = new UrlDTO(fileService.uploadFile(multipartFile));

        return ResponseEntity.ok(result);
    }
}
