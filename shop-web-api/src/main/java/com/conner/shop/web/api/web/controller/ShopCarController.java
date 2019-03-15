package com.conner.shop.web.api.web.controller;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.OrderCar;
import com.conner.shop.web.api.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "car")
public class ShopCarController {

    @Autowired
    ShopCarService shopCarService;

    /**
     * 加入购物车
     * @param orderCar
     * @return
     */
    @RequestMapping(value = "join", method = RequestMethod.POST)
    public BaseResult joinCar(OrderCar orderCar) {
//        orderCar.setUId(uId);
        return shopCarService.jonCar(orderCar);
    }

    /**
     * 获取购物车数据
     * @return
     */
    @RequestMapping(value = "pagelist", method = RequestMethod.GET)
    public BaseResult pageList(String uId) {
        return shopCarService.getPageList(uId);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(OrderCar orderCar) {
        return shopCarService.delete(orderCar);
    }
}
