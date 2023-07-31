package com.example.application.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidationMessageUtils {

    public static String extractErrorMessage(BindingResult bindingResult, String fieldName) {
        for (FieldError fieldError : bindingResult.getFieldErrors(fieldName)) {
            return fieldError.getDefaultMessage();
        }
        return null;
    }

    public static List<String> extractErrorMessages(BindingResult bindingResult, String fieldName1, String fieldName2) {
        List<String> errorMessages = new ArrayList<>();


        List<FieldError> fieldErrors1 = bindingResult.getFieldErrors(fieldName1);
        for (FieldError fieldError : fieldErrors1) {
            errorMessages.add(fieldError.getDefaultMessage());
        }


        List<FieldError> fieldErrors2 = bindingResult.getFieldErrors(fieldName2);
        for (FieldError fieldError : fieldErrors2) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return errorMessages;
    }
}
