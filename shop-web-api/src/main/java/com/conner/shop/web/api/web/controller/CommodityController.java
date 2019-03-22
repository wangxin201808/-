package com.conner.shop.web.api.web.controller;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.commons.utils.FileReceive;
import com.conner.shop.domain.Furniture;
import com.conner.shop.web.api.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("commodity")
public class CommodityController {


    @Autowired
    CommodityService commodityService;

    /**
     * 获取分页数据
     * @param page
     * @param length
     * @param name
     * @return
     */
    @RequestMapping(value = "listpage", method = RequestMethod.GET)
    public BaseResult getPageList(int page, int length, String name){
        BaseResult baseResult = commodityService.getPageList(page, length, name);
        return baseResult;
    }

    /**
     * 上传图片
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public BaseResult upLoadPic(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", FileReceive.receive(multipartFile, request));
        return BaseResult.success("图片上传成功",result);
    }

    /**
     * 添加商品
     * @param furniture
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult addCommodity(Furniture furniture) {
        return commodityService.addFurniture(furniture);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchremove", method = RequestMethod.POST)
    public BaseResult batchRemove(String ids){
        String[] split = ids.split(",");
        return commodityService.batchremove(split);
    }

    /**
     * 设置推荐商品
     * @param ids
     * @param type 热销商品，或是推荐商品
     * @return
     */
    @RequestMapping(value = "setrecommend", method = RequestMethod.POST)
    public BaseResult setRecommend(String ids, Integer type) {
        String[] split = ids.split(",");
        return commodityService.setRecommend(split, type);
    }

    /**
     * 得到推荐商品
     * @param
     * @return
     */
    @RequestMapping(value = "getrecommend", method = RequestMethod.GET)
    public BaseResult setHot () {
        return commodityService.getRecommend();
    }

    /**
     * 得到商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "getdetail", method = RequestMethod.GET)
    public BaseResult getDetail (String id) {
        return commodityService.getDetail(id);
    }

    /**
     * 得到热销商品
     * @return
     */
    @RequestMapping(value = "gethot", method = RequestMethod.GET)
    public BaseResult getHot () {
        return commodityService.getHot();
    }

    /**
     * 得到最新商品
     * @return
     */
    @RequestMapping(value = "getnews", method = RequestMethod.GET)
    public BaseResult getNews () {
        return commodityService.getPageList(0, 6, null) ;
    }

    /**
     * 搜索商品
     * @param furniture
     * @param lowPrice
     * @param highPrice
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public BaseResult search(Furniture furniture,Integer lowPrice, Integer highPrice){
        return commodityService.search(furniture, lowPrice, highPrice);
    }
}
