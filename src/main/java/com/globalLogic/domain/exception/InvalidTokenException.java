package com.globalLogic.domain.exception;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(String token_inválido) {
        super(token_inválido);
    }
}
