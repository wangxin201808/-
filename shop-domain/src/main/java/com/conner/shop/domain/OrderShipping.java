package com.conner.shop.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderShipping {
    private String shopId;
    private String userId;
    private String ping;
    private Date created;
}
