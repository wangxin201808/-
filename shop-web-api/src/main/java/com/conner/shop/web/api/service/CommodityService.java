package com.conner.shop.web.api.service;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.Furniture;

public interface CommodityService {

    /**
     * 获取分页数据
     * @param page
     * @param length
     * @return
     */
    BaseResult getPageList(int page, int length, String name);

    /**
     * 添加个修改商品
     * @param furniture
     */
    BaseResult addFurniture(Furniture furniture);

    /**
     * 批量删除
     * @param split
     * @return
     */
    BaseResult batchremove(String[] split);

    /**
     * 设为推荐商品
     * @param split
     * @return
     */
    BaseResult setRecommend(String[] split, Integer type);

    /**
     * 得到推荐商品
     * @return
     */
    BaseResult getRecommend();

    /**
     * 得到商品详情
     * @param id
     * @return
     */
    BaseResult getDetail(String id);

    /**
     * 得到热销商品
     * @return
     */
    BaseResult getHot();

    /**
     * 搜索商品
     * @param furniture
     * @param lowPrice
     * @param highPrice
     * @return
     */
    BaseResult search(Furniture furniture, Integer lowPrice, Integer highPrice);
}
