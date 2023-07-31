package com.example.application.util.handler;

import com.example.application.util.exception.InvalidAccessOperationException;
import com.example.application.util.exception.PasswordMismatchException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class CommunityExceptionHandler {

    @ExceptionHandler(InvalidAccessOperationException.class)
    public String handleInvalidAccessOperationException(InvalidAccessOperationException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/community";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", "태그에서 오류가 발생했습니다. 다시 시도 해주세요.");
        return "redirect:/community";
    }
}
