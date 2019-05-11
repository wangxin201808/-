package com.conner.shop.web.api.dao;

import com.conner.shop.domain.OrderShipping;


import java.util.Date;
import java.util.List;
public interface OrderShippingDao {
    // 根据商品id查询
    List<OrderShipping> getInfo(String shopId);

    // 保存数据
    Integer save(OrderShipping orderShipping);
}
