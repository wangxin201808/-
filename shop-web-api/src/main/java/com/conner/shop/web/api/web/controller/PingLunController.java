package com.conner.shop.web.api.web.controller;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.OrderShipping;
import com.conner.shop.web.api.dao.UserDao;
import com.conner.shop.web.api.service.RateServer;
import com.conner.shop.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "pinglun")
public class PingLunController {

    @Autowired
    RateServer rateServer;

    @Autowired
    UserDao userDao;

    /**
     * 获取商品 id
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getInfo", method = RequestMethod.GET)
    public BaseResult getPinglun(String shopId) {
        List<OrderShipping> result = rateServer.getInfo(shopId);
        return BaseResult.success("succeess", result);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResult save(OrderShipping orderShipping) {
        orderShipping.setUserId(userDao.getUserById(orderShipping.getUserId()).getName());
        Boolean result = rateServer.save(orderShipping);
        if (result) {
            return BaseResult.success();
        }
        return BaseResult.fail();
    }
}
