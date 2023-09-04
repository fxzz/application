package com.example.application.image.mapper;

import com.example.application.image.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageReadMapper {
    List<ImageDto> selectImage(Long communityId);
}
