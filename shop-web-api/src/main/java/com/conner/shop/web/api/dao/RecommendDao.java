package com.conner.shop.web.api.dao;

import com.conner.shop.domain.Recommend;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendDao {
    /**
     * 新增数据
     * @param recommend
     * @return
     */
    Integer save(Recommend recommend);

    /**
     * 根据 id 获取信息
     * @param id
     * @return
     */
    Recommend getById(String id);

    /**
     * 更新 数据
     * @param hasRecommend
     * @return
     */
    Integer update(Recommend hasRecommend);

    /**
     * 得到 最近设置的6个商品
     * @return
     */
    List<Recommend> getPage();
}
