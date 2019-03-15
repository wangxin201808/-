package com.conner.shop.commons.constant;

public class ConstantUtils {
    public static final String SESSION_USER = "user";

    /**
     * 验证手机号
     */
    public static final String PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 验证邮箱地址
     */
    public static final String EMAIL = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";

    /**
     * 文件上传到 target 中的位置
     */
    public static final String UPLOAD_PATH = "/static/upload/";

    /**
     * 文件上传到 本机 中的位置
     */
    public static final String UPLOAD_ACCESS_PATH = "F:/upload/";

}
