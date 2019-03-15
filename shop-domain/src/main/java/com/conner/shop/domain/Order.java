package com.conner.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private String orderId;
    private String userId;
    private String shippingName;
    private String shippingCode;
    private String itemId;
    private int num;
    private String title;
    private Float price;
    private Float totalFee;
    private String picPath;
    private String receiverName;
    private String receiverMobile;
    private String receiverState;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private String receiverZip;
    @JsonIgnore
    private Date carCreated;
    private String payment;
    private int status;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date paymentTime;
    @JsonIgnore
    private Date consignTime;
    @JsonIgnore
    private Date endTime;


}
