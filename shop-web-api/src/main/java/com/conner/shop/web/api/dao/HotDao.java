package com.conner.shop.web.api.dao;

import com.conner.shop.domain.Hot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotDao {
    /**
     * 通过 id 获取
     * @param id
     * @return
     */
    Hot getById(String id);

    /**
     * 保存信息
     * @param hot
     * @return
     */
    Integer save(Hot hot);

    /**
     * 更新信息
     * @param hasHot
     * @return
     */
    Integer update(Hot hasHot);

    /**
     * 得到热门信息
     * @return
     */
    List<Hot> getHotPage();
}
