package com.conner.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Data
public class Furniture {
    private String id;
    @NotEmpty(message = "商品名称不能为空")
    private String name;
    private Integer oldPrice;
    private Integer price;
    private String parameter ;
    private String feature ;
    private String sort ;
    private String style ;
    @NotEmpty(message = "图片不能少于四张")
    private String img1 ;
    @NotEmpty(message = "图片不能少于四张")
    private String img2 ;
    @NotEmpty(message = "图片不能少于四张")
    private String img3 ;
    @NotEmpty(message = "图片不能少于四张")
    private String img4;
    @JsonIgnore
    private Date created;
}
