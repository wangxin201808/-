package com.conner.shop.web.api.service;

import com.conner.shop.domain.OrderShipping;

import java.util.List;

public interface RateServer {

    public List<OrderShipping> getInfo(String shopId);

    Boolean save(OrderShipping orderShipping);
}
