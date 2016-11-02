package com.bob.springboot.simple.model.response;

import com.bob.springboot.simple.enums.ResponseEnum;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2016/10/30.
 */
public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = -1845789647926148L;

    private String code;
    private String message;
    private T result;


    private ResultResponse() {}

    private ResultResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private ResultResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    private ResultResponse(ResponseEnum responseEnum, T result) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.result = result;
    }

    public static ResultResponse success() {
        return new ResultResponse(ResponseEnum.SUCCESS);
    }

    public static <T> ResultResponse<T> success(T result) {
        return new ResultResponse<T>(ResponseEnum.SUCCESS, result);
    }

    public static ResultResponse failed() {
        return new ResultResponse(ResponseEnum.FAILED);
    }

    public static ResultResponse failed(String message) {
        return new ResultResponse(ResponseEnum.FAILED.getCode(), message, null);
    }

    public static ResultResponse failed(String code, String message) {
        return new ResultResponse(code, message, null);
    }

    public static ResultResponse failed(ResponseEnum responseEnum) {
        return new ResultResponse(responseEnum);
    }

    public static ResultResponse define(String code, String message) {
        return new ResultResponse(code, message, null);
    }

    public static ResultResponse define(ResponseEnum responseEnum) {
        return new ResultResponse(responseEnum);
    }

    public static <T> ResultResponse<T> define(ResponseEnum responseEnum, T result) {
        return new ResultResponse<T>(responseEnum, result);
    }

    public static <T> ResultResponse<T> define(String code, String message, T result) {
        return new ResultResponse<T>(code, message, result);
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
