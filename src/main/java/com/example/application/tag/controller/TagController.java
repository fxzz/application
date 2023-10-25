package com.example.application.tag.controller;

import com.example.application.tag.dto.TagFormDto;
import com.example.application.tag.service.TagService;
import com.example.application.tag.validator.TagAddValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagAddValidator tagAddValidator;

    @InitBinder("tagFormDto")
    public void tagInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(tagAddValidator);
    }



    @PostMapping("/tag/add")
    public ResponseEntity addTag(@Valid @RequestBody TagFormDto tagFormDto) {
        tagService.addTagIfNotExists(tagFormDto.getTagTitle());
        return ResponseEntity.ok().build();
    }


}
