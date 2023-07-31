package com.example.application.tag.service;

import java.util.List;

public interface TagService {
    void insertTag(String tagTitle);
    int selectTitleCount(String tagTitle);
    void addTagIfNotExists(String tagTitle);
    List<String> selectAllTag();
    int selectTagId(String tagTitle);
}
