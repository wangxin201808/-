package com.conner.shop.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderCar {
    private String id;
    private String uId;
    private Integer num;
    private Date created;
}
