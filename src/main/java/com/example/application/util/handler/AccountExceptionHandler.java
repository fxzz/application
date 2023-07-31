package com.example.application.util.handler;

import com.example.application.util.exception.PasswordMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public String handlePasswordMismatchException(PasswordMismatchException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/profile/password";
    }
}
