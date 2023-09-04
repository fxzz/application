package com.example.application.image.service;

import com.example.application.image.dto.ImageDto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<ImageDto> selectImage(Long communityId);
    void uploadFile(MultipartFile[] files, Long communityId, String uploadPath) throws IOException;
    boolean deleteFile(String newFilename, String uploadPath);
}
