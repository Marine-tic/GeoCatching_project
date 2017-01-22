package com.IutNiceGroupeB.app;

import java.io.IOException;

public class AuthorizationCheckException extends Exception {

    public AuthorizationCheckException(String message) {
        super(message);
    }

    public AuthorizationCheckException(String message, Throwable e) {
        super(message, e);
    }
}
