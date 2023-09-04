package com.example.application.image.mapper;

import com.example.application.image.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageWriteMapper {

   void insertImage(ImageDto imageDto);
   void deleteImage(String newFilename);
}
