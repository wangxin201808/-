package com.conner.shop.commons.dto;

import com.fasterxml.jackson.databind.ser.Serializers;

public class BaseResult {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;

    private int status;
    private String message;
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int stauts) {
        this.status = stauts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private static BaseResult createBaseResult(int stauts, String message, Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(stauts);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public static BaseResult success() {
        return BaseResult.createBaseResult(STATUS_SUCCESS, "成功", null);
    }

    public static BaseResult success(String message) {
        return BaseResult.createBaseResult(STATUS_SUCCESS, message, null);
    }

    public static BaseResult success(String message, Object data) {
        return BaseResult.createBaseResult(STATUS_SUCCESS, message, data);
    }

    public static BaseResult fail() {
        return BaseResult.createBaseResult(STATUS_FAIL, "失败", null);
    }

    public static BaseResult fail(String message) {
        return BaseResult.createBaseResult(STATUS_FAIL, message, null);
    }

    public static BaseResult fail(String message, Object data) {
        return BaseResult.createBaseResult(STATUS_FAIL, message, data);
    }

}
