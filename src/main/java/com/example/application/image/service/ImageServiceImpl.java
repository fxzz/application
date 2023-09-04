package com.example.application.image.service;

import com.example.application.community.dto.CommunityImageEnabledDto;
import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.image.dto.ImageDto;
import com.example.application.image.mapper.ImageReadMapper;
import com.example.application.image.mapper.ImageWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageWriteMapper imageWriteMapper;
    private final ImageReadMapper imageReadMapper;
    private final CommunityWriteMapper communityWriteMapper;


    @Transactional(readOnly = true)
    @Override
    public List<ImageDto> selectImage(Long communityId) {
        return imageReadMapper.selectImage(communityId);
    }


    @Override
    public void uploadFile(MultipartFile[] files, Long communityId, String uploadPath) throws IOException {
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();

            // 파일 확장자 검사
            if (originalFilename != null) {
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                if (isAllowedExtension(fileExtension)) {
                    // 허용된 확장자인 경우에만 파일을 저장하고 리스트에 추가
                    String newFilename = UUID.randomUUID().toString() + "." + fileExtension;

                    File upFile = new File(uploadPath, newFilename);
                    file.transferTo(upFile);

                    var imageDto = ImageDto.of(newFilename, originalFilename, communityId);
                    imageWriteMapper.insertImage(imageDto);

                    communityWriteMapper.updateCommunityImageEnabled(new CommunityImageEnabledDto(communityId, TRUE));
                } else {
                    // 허용되지 않은 확장자인 경우 에러 처리 혹은 무시
                    System.out.println("허용되지 않은 파일 확장자: " + fileExtension);
                    //todo : 나중에 익셉션 날려서 받기
                }
            }
        }
    }

    @Transactional
    @Override
    public boolean deleteFile(String newFilename, String uploadPath) {
        File file = new File(uploadPath + newFilename);
        if (file.delete()) {
            imageWriteMapper.deleteImage(newFilename);
            return true;
        } else {
            return false;
        }
    }


    private boolean isAllowedExtension(String fileExtension) {
        // 허용할 파일 확장자 목록 설정
        List<String> allowedExtensions = Arrays.asList("jpg", "png");
        return allowedExtensions.contains(fileExtension.toLowerCase());
    }


}
