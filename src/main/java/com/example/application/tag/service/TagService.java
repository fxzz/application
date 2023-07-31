package com.example.application.tag.service;

import java.util.List;

public interface TagService {

    void addTagIfNotExists(String tagTitle);
    List<String> selectAllTag();
    int selectTagId(String tagTitle);
}
