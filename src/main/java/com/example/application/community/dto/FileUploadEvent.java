package com.example.application.community.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadEvent {
   private MultipartFile[] files;
   private Long communityId;
   private String uploadPath;

    private FileUploadEvent(MultipartFile[] files, Long communityId, String uploadPath) {
        this.files = files;
        this.communityId = communityId;
        this.uploadPath = uploadPath;
    }

    public static FileUploadEvent createEvent(MultipartFile[] files, Long communityId, String uploadPath) {
        return new FileUploadEvent(files, communityId, uploadPath);
    }
}
