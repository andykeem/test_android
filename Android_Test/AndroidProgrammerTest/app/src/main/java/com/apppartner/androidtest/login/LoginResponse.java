package com.apppartner.androidtest.login;

/**
 * Created by foo on 2/17/18.
 */

public class LoginResponse {
    protected static final String CODE_SUCCESS = "success";
    protected String code;
    protected String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return (code.equalsIgnoreCase(CODE_SUCCESS));
    }
}
