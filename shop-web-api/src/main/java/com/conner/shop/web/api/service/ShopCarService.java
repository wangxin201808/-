package com.conner.shop.web.api.service;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.OrderCar;

public interface ShopCarService {
    /**
     * 加入购物车
     * @param orderCar
     * @return
     */
    BaseResult jonCar(OrderCar orderCar);

    /**
     * 获取购物车数据
     * @param uId
     * @return
     */
    BaseResult getPageList(String uId);

    /**
     * 删除一条购物车记录
     * @param orderCar
     * @return
     */
    BaseResult delete(OrderCar orderCar);
}
