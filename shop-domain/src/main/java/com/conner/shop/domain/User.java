package com.conner.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private String id;
    private int role;
    @Length(min = 2, max = 20, message = "姓名的长度必须介于 2 - 20 位之间")
    private String name;
    @JsonIgnore
    @Length(min = 6, message = "密码长度不能低于6位")
    private String password;
//    @Pattern(regexp = ConstantUtils.PHONE, message = "手机号格式不正确")
    private String phone;
//    @Pattern(regexp = ConstantUtils.EMAIL, message = "邮箱格式不正确")
    private String email;
//    @DateTimeFormat(pattern="yyyy-MM-dd")//页面写入数据库时格式化
//    @JSONField(format="yyyy-MM-dd")//数据库导出页面时json格式化
    private String birth;
    private String sex;
    private String hobby;
    private String address;
    @JsonIgnore
    private Date created;

}
