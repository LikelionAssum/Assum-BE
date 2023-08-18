package com.aledma.hackathonBEfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "일치하는 정보가 없습니다.")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }
}