package com.example.application.tag.service;


import com.example.application.tag.mapper.TagReadMapper;
import com.example.application.tag.mapper.TagWriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagWriteMapper tagWriteMapper;
    private final TagReadMapper tagReadMapper;




    @Override
    public void addTagIfNotExists(String tagTitle) {
        if (!isTagExists(tagTitle)) {
            tagWriteMapper.insertTag(tagTitle);
        }
    }

    @Transactional(readOnly = true)
    public boolean isTagExists(String tagTitle) {
        return tagReadMapper.selectTitleCount(tagTitle) > 0;
    }



    @Transactional(readOnly = true)
    @Override
    public List<String> selectAllTag() {
        return tagReadMapper.selectAllTag();
    }

    @Override
    public int selectTagId(String tagTitle) {
        return tagReadMapper.selectTagId(tagTitle);
    }
}
