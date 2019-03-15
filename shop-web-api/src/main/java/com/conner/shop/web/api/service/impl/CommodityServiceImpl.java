package com.conner.shop.web.api.service.impl;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.commons.utils.BeanValidator;
import com.conner.shop.domain.Furniture;
import com.conner.shop.domain.Hot;
import com.conner.shop.domain.Recommend;
import com.conner.shop.web.api.dao.CommodityDao;
import com.conner.shop.web.api.dao.HotDao;
import com.conner.shop.web.api.dao.RecommendDao;
import com.conner.shop.web.api.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityDao commodityDao;

    @Autowired
    RecommendDao recommendDao;

    @Autowired
    HotDao hotDao;

    @Override
    public BaseResult getPageList(int page, int length, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page*length);
        map.put("length", length);
        map.put("name", name);
        List<Furniture> commodityList = commodityDao.getPageCommodity(map);
        int count = commodityDao.countAll(name);

        Map<String, Object> result = new HashMap<>();
        result.put("furnitures", commodityList);
        result.put("total", count);

        return BaseResult.success("成功", result);
    }

    @Override
    public BaseResult addFurniture(Furniture furniture) {
        String validator = BeanValidator.validator(furniture);
        // 验证失败
        if (validator != null) {
            return BaseResult.fail(validator);
        }
        // 验证成功
        else {
            Integer i;
            // 添加操作
            if (furniture.getId() == null) {
                furniture.setId(UUID.randomUUID().toString().replace("-", ""));
                furniture.setCreated(new Date());
                i = commodityDao.save(furniture);
            }
            // 修改操作
            else {
                i = commodityDao.update(furniture);
            }
            if ( i == null) {
                return BaseResult.fail();
            }
            return BaseResult.success();
        }
    }

    @Override
    public BaseResult batchremove(String[] split) {
        Integer i = null;
        for(String id : split) {
            i = commodityDao.removeById(id);
        }
        if (i != null) {
            return BaseResult.success("信息删除成功");
        } else {
            return BaseResult.fail("信息删除失败");
        }

    }

    @Override
    public BaseResult setRecommend(String[] split, Integer type) {

        Integer i = null;
        //设置推荐商品
        if (type == 1) {
            for (String id : split){
                Recommend hasRecommend = recommendDao.getById(id);
                // 当推荐栏中没有时保存商品
                if (hasRecommend == null) {
                    Recommend recommend = new Recommend();
                    recommend.setCreated(new Date());
                    recommend.setCarouselId(id);
                    i = recommendDao.save(recommend);
                }

                // 当有时，更新时间
                else {
                    hasRecommend.setCreated(new Date());
                    i = recommendDao.update(hasRecommend);
                }

            }
        }

        // 设置热销商品
        if (type == 0) {
            for (String id : split){
                Hot hasHot = hotDao.getById(id);
                // 当推荐栏中没有时保存商品
                if (hasHot == null) {
                    Hot hot = new Hot();
                    hot.setCreated(new Date());
                    hot.setHotId(id);
                    i = hotDao.save(hot);
                }

                // 当有时，更新时间
                else {
                    hasHot.setCreated(new Date());
                    i = hotDao.update(hasHot);
                }

            }
        }

        if (i != null) {
            return BaseResult.success("设置成功");
        }
        return BaseResult.fail("商品设置失败");
    }

    @Override
    public BaseResult getRecommend() {
        List<Map<String, String>> result = new ArrayList<>() ;
        List<Recommend> recommends = recommendDao.getPage();
        for( int i = 0 ; i < recommends.size() ; i++) {
            String img = commodityDao.getPicById(recommends.get(i).getCarouselId());
            Map<String, String> map = new HashMap<>();
            map.put("id", recommends.get(i).getCarouselId());
            map.put("img", img);
            result.add(map);
        }
        return BaseResult.success("success", result);
    }

    @Override
    public BaseResult getDetail(String id) {
        Furniture furniture = commodityDao.getInfoById(id);
        if (furniture != null) {
            return BaseResult.success("success", furniture);
        }
        return BaseResult.fail("服务器boom");
    }

    @Override
    public BaseResult getHot() {

        List<Map<String, Object>> list = new ArrayList<>();

        List<Hot> hots = hotDao.getHotPage();
        for (int i = 0; i < hots.size(); i++) {
            String hotId = hots.get(i).getHotId();
            Furniture infoById = commodityDao.getInfoById(hotId);
            Map<String, Object> map = new HashMap<>();
            map.put("img", infoById.getImg1());
            map.put("id", infoById.getId());
            map.put("name",infoById.getName());
            map.put("price", infoById.getPrice());
            map.put("oldPrice", infoById.getOldPrice());
            list.add(map);
        }

        return BaseResult.success("成功获取信息", list);
    }

    @Override
    public BaseResult search(Furniture furniture, Integer lowPrice, Integer highPrice) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", furniture.getName());
        params.put("feature", furniture.getFeature());
        params.put("sort", furniture.getSort());
        params.put("style", furniture.getStyle());
        params.put("lowPrice", lowPrice);
        params.put("highPrice", highPrice);
        List<Furniture> list = commodityDao.search(params);
        return BaseResult.success("success", list);
    }

}
