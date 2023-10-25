package com.example.application.tag.validator;

import com.example.application.tag.dto.TagFormDto;
import com.example.application.util.exception.CommunityIdValidationException;
import com.example.application.util.exception.CustomApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class TagAddValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(TagFormDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
      TagFormDto tagFormDto = (TagFormDto) target;

        if (!tagFormDto.getTagTitle().matches("^[가-힣ㄱ-ㅎㅏ-ㅣA-Za-z0-9]{1,15}$")) {
            throw new CustomApiException("태그 검증 에러");
        }
    }
}
