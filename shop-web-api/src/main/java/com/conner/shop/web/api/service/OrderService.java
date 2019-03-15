package com.conner.shop.web.api.service;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.Order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    /**
     * 获取用户列表
     * @param page
     * @param length
     * @param shippingCode
     * @param receiverName
     * @param receiverMobile
     * @return
     */
    BaseResult getPageList(int page, int length, String shippingCode, String receiverName, String receiverMobile, String userId);

    /**
     * 编辑订单
     * @param order
     * @return
     */
    BaseResult edit(Order order, HttpServletRequest request);

    /**
     * 批量删除
     * @param split
     * @return
     */
    BaseResult batchremove(String[] split);

    /**
     * 增加订单
     * @param total_amount 总金额
     * @param out_trade_no 订单号
     * @param userId 用户id
     * @param itemId 商品id
     */
    Integer save(String total_amount, String out_trade_no, String userId, String itemId);

}
