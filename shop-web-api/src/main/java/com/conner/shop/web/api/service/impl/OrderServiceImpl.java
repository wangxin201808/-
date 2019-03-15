package com.conner.shop.web.api.service.impl;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.Furniture;
import com.conner.shop.domain.Order;
import com.conner.shop.domain.OrderCar;
import com.conner.shop.domain.User;
import com.conner.shop.web.api.dao.CommodityDao;
import com.conner.shop.web.api.dao.OrderCarDao;
import com.conner.shop.web.api.dao.OrderDao;
import com.conner.shop.web.api.dao.UserDao;
import com.conner.shop.web.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderCarDao orderCarDao;

    @Autowired
    CommodityDao commodityDao;

    @Autowired
    UserDao userDao;

    @Override
    public BaseResult getPageList(int page, int length, String shippingCode, String receiverName, String receiverMobile, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page*length);
        map.put("length", length);
        map.put("receiverName", receiverName);
        map.put("shippingCode", shippingCode);
        map.put("receiverMobile", receiverMobile);
        map.put("userId", userId);
        List<Order> orderList = orderDao.getPageOrder(map);
        int size = orderList.size();

        Map<String, Object> countParams = new HashMap<>();
        map.put("receiverName", receiverName);
        map.put("shippingCode", shippingCode);
        map.put("receiverMobile", receiverMobile);
        Integer count = null;
        if (size == 0){
            count = 0;
        } else {
            count = orderDao.countAll(countParams);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orders", orderList);
        result.put("total", count);

        return BaseResult.success("成功", result);
    }

    @Override
    public BaseResult edit(Order order, HttpServletRequest request) {

        Integer count = orderDao.update(order);
        if (count != null) {
          return BaseResult.success();
        }
        return BaseResult.fail();
    }

    @Override
    public BaseResult batchremove(String[] split) {
        Integer i = null;
        for(String id : split) {
            i = orderDao.removeById(id);
        }
        if (i != null) {
            return BaseResult.success("信息删除成功");
        } else {
            return BaseResult.fail("信息删除失败");
        }

    }

    @Override
    public Integer save(String total_amount, String out_trade_no, String userId, String itemId) {
        Integer i = null;
        String[] itemIds = itemId.split(",");
        for (String item : itemIds) {
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString().replace("-",""));
            order.setUserId(userId);
            order.setItemId(item);
            order.setShippingCode(out_trade_no);
            order.setShippingName("顺丰");


            Map<String, String> map = new HashMap<>();
            map.put("id", item);
            map.put("uId", userId);
            OrderCar orderCar = orderCarDao.getOneInfo(map);
            order.setNum(orderCar.getNum());

            Furniture furniture = commodityDao.getInfoById(item);
            float price = furniture.getPrice();
            order.setPrice(price);
            order.setPicPath(furniture.getImg1());
            order.setTotalFee(Float.parseFloat(total_amount));
            order.setTitle(furniture.getName());

            User user = userDao.getUserById(userId);
            order.setReceiverMobile(user.getPhone());
            order.setReceiverAddress(user.getAddress());
            order.setStatus(2);
            order.setCreateTime(new Date());

            OrderCar orderCar1 = new OrderCar();
            orderCar1.setId(item);
            orderCar1.setUId(userId);
            // 删除购物车中的信息
            Integer integer = orderCarDao.deleteById(orderCar1);
            if(integer == 1){
                i = orderDao.save(order);
            }

        }
        return i;
    }

}
