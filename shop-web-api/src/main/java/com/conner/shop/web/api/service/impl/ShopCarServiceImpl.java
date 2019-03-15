package com.conner.shop.web.api.service.impl;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.Furniture;
import com.conner.shop.domain.OrderCar;
import com.conner.shop.web.api.dao.CommodityDao;
import com.conner.shop.web.api.dao.OrderCarDao;
import com.conner.shop.web.api.dao.UserDao;
import com.conner.shop.web.api.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopCarServiceImpl implements ShopCarService {
    @Autowired
    UserDao userDao;

    @Autowired
    CommodityDao commodityDao;

    @Autowired
    OrderCarDao orderCarDao;

    @Override
    public BaseResult jonCar(OrderCar orderCar) {
        Integer num = null;
        String uId = orderCar.getUId();
        String id = orderCar.getId();
        // 判断用户是否存在
        List<OrderCar> orders = orderCarDao.getInfoByUID(uId);
        for (int i=0; i < orders.size(); i++){
            // 取出用户购买的商品id
            String id1 = orders.get(i).getId();
            // 判断商品是否存在
            if (id1.equals(id)) {
               num = orderCarDao.update(orderCar);
                if (num != null) {
                    return BaseResult.success("数据更新成功");
                }
                return BaseResult.fail("商品添加失败");
            }
        }
        // 用户记录不存在
        orderCar.setCreated(new Date());
        num = orderCarDao.save(orderCar);
        if (num != null) {
            return BaseResult.success("成功添加到购物车");
        }
        return BaseResult.fail("商品添加失败");
    }

    @Override
    public BaseResult getPageList(String uId) {

        List<Map<Object, Object>> list = new ArrayList<>();
        // 获得用户的所有商品 id
        List<OrderCar> orders = orderCarDao.getInfoByUID(uId);
        for (int i = 0; i < orders.size(); i++) {
            Map<Object, Object> map = new HashMap<>();
            String id = orders.get(i).getId();
            Integer num = orders.get(i).getNum();
            Furniture infoById = commodityDao.getInfoById(id);
            map.put("infoById", infoById);
            map.put("num", num);
            list.add(map);
        }

        return BaseResult.success("success", list);
    }

    @Override
    public BaseResult delete(OrderCar orderCar) {
        Integer i = null;
        i = orderCarDao.deleteById(orderCar);
        if (i != null) {
            return BaseResult.success();
        }
        return BaseResult.fail("服务器boom....");
    }
}
