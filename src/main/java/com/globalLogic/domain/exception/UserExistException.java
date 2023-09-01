package com.globalLogic.domain.exception;

public class UserExistException extends BusinessException{
    public UserExistException(String message) {
        super(message);
    }
}
