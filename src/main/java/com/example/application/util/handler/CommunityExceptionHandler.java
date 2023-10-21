package com.example.application.util.handler;

import com.example.application.util.exception.CommunityIdValidationException;
import com.example.application.util.exception.UnauthorizedAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class CommunityExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleInvalidAccessOperationException(UnauthorizedAccessException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/community";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", "태그에서 오류가 발생했습니다. 다시 시도 해주세요.");
        return "redirect:/community";
    }

    @ExceptionHandler(CommunityIdValidationException.class)
    public String handleInvalidAccessOperationException(CommunityIdValidationException ex, RedirectAttributes attributes) {
        log.info("CommunityIdValidationException : {}", ex.getMessage());
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/community";
    }
}
