package com.conner.shop.web.api.dao;

import com.conner.shop.domain.Furniture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommodityDao {

    /**
     * 分页获取数据
     * @param map
     * @return
     */
    List<Furniture> getPageCommodity(Map<String, Object> map);

    /**
     * 计算总条数
     * @return
     */
    int countAll(@Param("name") String name);

    /**
     * 数据保存
     * @param furniture
     */
    int save(Furniture furniture);

    /**
     * 更新数据
     * @param furniture
     * @return
     */
    Integer update(Furniture furniture);

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    Integer removeById(String id);

    /**
     * 根据 id 获取图片
     * @return
     */
    String getPicById(String id);

    /**
     * 根据 id 获取信息
     * @param id
     * @return
     */
    Furniture getInfoById(String id);

    /**
     * 根据参数查询商品信息
     * @param params
     * @return
     */
    List<Furniture> search(Map<String, Object> params);
}
