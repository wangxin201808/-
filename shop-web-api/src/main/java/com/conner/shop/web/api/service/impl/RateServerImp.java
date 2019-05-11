package com.conner.shop.web.api.service.impl;

import com.conner.shop.domain.OrderShipping;
import com.conner.shop.web.api.dao.OrderShippingDao;
import com.conner.shop.web.api.service.RateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class RateServerImp implements RateServer {

    @Autowired
    OrderShippingDao orderShippingDao;

    /**
     * 获取评论
     * @param shopId
     * @return
     */
    @Override
    public List<OrderShipping> getInfo(String shopId) {
        List<OrderShipping> result = orderShippingDao.getInfo(shopId);
        return result;
    }

    /**
     * 保存评论
     * @param orderShipping
     * @return
     */
    @Override
    public Boolean save(OrderShipping orderShipping) {
        orderShipping.setCreated(new Date());
        Integer i = orderShippingDao.save(orderShipping);
        if (i != 0) {
            return true;
        }
        return false;
    }
}
