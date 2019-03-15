package com.conner.shop.web.api.dao;

import com.conner.shop.domain.OrderCar;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderCarDao {

    /**
     * 新增数据
     * @param orderCar
     * @return
     */
    Integer save(OrderCar orderCar);

    /**
     * 得到用户所有的所有购物车记录
     * @param uId
     * @return
     */
    List<OrderCar> getInfoByUID(String uId);

    /**
     * 更新数据
     * @param orderCar
     * @return
     */
    Integer update(OrderCar orderCar);

    /**
     * 根据 id 删除记录
     * @param orderCar
     * @return
     */
    Integer deleteById(OrderCar orderCar);

    /**
     * 得到用户到的一条记录
     * @return
     */
    OrderCar getOneInfo(Map<String, String> map);
}
