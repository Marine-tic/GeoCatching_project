package com.IutNiceGroupeB.app;

public class UnauthorizedException extends AuthorizationCheckException {

    public UnauthorizedException(String s) {
        super(s);
    }
}
