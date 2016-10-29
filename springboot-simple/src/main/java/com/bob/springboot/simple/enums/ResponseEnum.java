package com.bob.springboot.simple.enums;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public enum ResponseEnum {

    SUCCESS("200", "success!"),
    FAILED("-10001", "failed!"),
    ;

    private String code;
    private String message;

    private ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
