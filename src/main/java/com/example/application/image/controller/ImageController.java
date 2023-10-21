package com.example.application.image.controller;

import com.example.application.image.dto.ImageDto;
import com.example.application.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @Value("${uploadPath}")
    private String uploadPath;


    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/file-list")
    @ResponseBody
    public ResponseEntity<List<ImageDto>> fileList(Long communityId) {
        List<ImageDto> images = imageService.selectImage(communityId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String filename) {
        Resource resource = new FileSystemResource(uploadPath + filename);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }



    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String newFilename) {

        boolean isDeleted = imageService.deleteFile(newFilename, uploadPath);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
