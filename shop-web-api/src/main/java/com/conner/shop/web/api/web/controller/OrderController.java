package com.conner.shop.web.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.Order;
import com.conner.shop.web.api.service.OrderService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 分页获取数据
     * @param page
     * @param length
     * @param shippingCode
     * @param receiverName
     * @param receiverMobile
     * @return
     */
    @RequestMapping(value = "listpage", method = RequestMethod.GET)
    public BaseResult getPageList(int page, int length, String shippingCode, String receiverName, String receiverMobile, String userId){
        BaseResult baseResult = orderService.getPageList(page, length, shippingCode, receiverName, receiverMobile, userId);
        return baseResult;
    }

    /**
     * 编辑订单
     * @param order
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseResult save(Order order, HttpServletRequest request) {

        return orderService.edit(order, request);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchremove", method = RequestMethod.POST)
    public BaseResult batchRemove(String ids){
        String[] split = ids.split(",");
        return orderService.batchremove(split);
    }


    /**
     * 查询订单
     * @param id 订单号
     * @param com 公司名称
     */
    @RequestMapping("address")
    public BaseResult getAddress(String id, String com) {


        // 创建 http 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 http 请求
        HttpGet httpGet = new HttpGet("http://v.juhe.cn/exp/index?key=14855748fba51bb1eb9c5d10eb31e1ab&com=" + com + "&no=" + id);

        // 设置长连接
        httpGet.setHeader("Connction", "keep-alive");
        // 设置代理
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        CloseableHttpResponse httpResponse = null;
        JSONObject result = null;
        try {
            // 请求并获得响应结果
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            result = JSONObject.parseObject(EntityUtils.toString(httpEntity));
            // 输出请求结果
            System.out.println(result);
//            String s = EntityUtils.toString(httpEntity);
//            String substring = s.substring(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 无论如何必须关闭连接
        finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return BaseResult.success("success", result);
    }

}
