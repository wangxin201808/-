package com.conner.shop.web.api.dao;

import com.conner.shop.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderDao {

    /**
     * 获取订单列表
     * @param map
     * @return
     */
    List<Order> getPageOrder(Map<String, Object> map);

    /**
     * 获取总数
     * @param countParams
     * @return
     */
    int countAll(Map<String, Object> countParams);

    /**
     * 更新订单
     * @param order
     * @return
     */
    Integer update(Order order);

    /**
     * 通过 id 删除订单
     * @param id
     * @return
     */
    Integer removeById(String id);

    /**
     * 添加订单
     * @param order
     * @return
     */
    Integer save(Order order);
}
