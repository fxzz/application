package com.example.application.tag.service;

import com.example.application.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;


    @Override
    public void insertTag(String tagTitle) {
        tagMapper.insertTag(tagTitle);
    }

    @Transactional(readOnly = true)
    @Override
    public int selectTitleCount(String tagTitle) {
        return tagMapper.selectTitleCount(tagTitle);
    }



    @Override
    public void addTagIfNotExists(String tagTitle) {
        if (!isTagExists(tagTitle)) {
            insertTag(tagTitle);
        }
    }

    @Transactional(readOnly = true)
    public boolean isTagExists(String tagTitle) {
        return selectTitleCount(tagTitle) > 0;
    }



    @Transactional(readOnly = true)
    @Override
    public List<String> selectAllTag() {
        return tagMapper.selectAllTag();
    }

    @Override
    public int selectTagId(String tagTitle) {
        return tagMapper.selectTagId(tagTitle);
    }
}
