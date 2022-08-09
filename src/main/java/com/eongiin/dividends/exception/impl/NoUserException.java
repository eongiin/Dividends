package com.eongiin.dividends.exception.impl;

import com.eongiin.dividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NoUserException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 유저입니다";
    }
}
