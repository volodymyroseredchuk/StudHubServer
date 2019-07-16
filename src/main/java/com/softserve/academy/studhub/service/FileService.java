package com.softserve.academy.studhub.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

   String uploadFile(MultipartFile toUpload) throws IOException;
}
