package com.seoplog.exception;

public class ExistsUsernameException extends seopLogException{

    public static final String MESSAGE = "이미 존재하는 아이디입니다.";

    public ExistsUsernameException() {
        super(MESSAGE);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
