package com.example.application.tag.controller;

import com.example.application.tag.dto.TagFormDto;
import com.example.application.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    @PostMapping("/tag/add")
    public ResponseEntity addTag(@RequestBody TagFormDto tagFormDto) {
        tagService.addTagIfNotExists(tagFormDto.getTagTitle());
        return ResponseEntity.ok().build();
    }


}
