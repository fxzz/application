package com.example.application.community.service;

import com.example.application.community.dto.FileUploadEvent;
import com.example.application.image.service.ImageService;
import com.example.application.util.exception.ImageUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CommunityFileUploadedListener {

    private final ImageService imageService;

    @TransactionalEventListener
    public void handleFileUploadedEvent(FileUploadEvent event) {
        try {
            imageService.uploadFile(event.getFiles(), event.getCommunityId(), event.getUploadPath());
        } catch (IOException e) {
            throw new ImageUploadException("Image upload failed.");
        }
    }
}
