package com.conner.shop.commons.factory;

public class AlipayClientFactory {
//    private static final AlipayClient client = new DefaultAlipayClient(
//            AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT,
//            AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
//
//    public static AlipayClient getAlipayClientInstance() {
//        return client;
//    }
//
//    /**
//     *  
//     *      * appAuthToken 
//     *      * 如ISV代替商家调用当面付接口，需将商户授权后获取的app_auth_token带上；如商家申请当面付自己调用，则传null bizContent 
//     *      * JSON格式 商户的请求参数 
//     *      
//     */
//
//    // 手机网页支付 网站支付 
//    public String ydAndPc_Pay(Map<String, String> maps)
//            throws AlipayApiException {
//        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
//        String NotifyUrl = maps.get("NotifyUrl");
//        String ReturnUrl = maps.get("ReturnUrl");
//        // 后台回调 
//        if (!StringUtils.isEmpty(NotifyUrl)) {
//            alipayRequest.setNotifyUrl(NotifyUrl);
//            // bizContent 中不需要 公共参数 
//            maps.remove("NotifyUrl");
//        }
//        // 页面回调 
//        if (!StringUtils.isEmpty(ReturnUrl)) {
//            alipayRequest.setReturnUrl(ReturnUrl);
//            // bizContent 中不需要 公共参数 
//            maps.remove("ReturnUrl");
//        }
//        String bizCon = JSON.toJSONString(maps);
//        alipayRequest.setBizContent(bizCon);
//        String form = "";
//        try {
//            form = AlipayClientFactory.getAlipayClientInstance().pageExecute(alipayRequest).getBody();
//        } catch (AlipayApiException e) {
//            form = "err";
//            e.printStackTrace();
//        } // 调用SDK生成表单 
//        return form;
//    }
//
//    public String Pc_Pay(Map<String, String> maps) throws AlipayApiException {
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        String NotifyUrl = maps.get("NotifyUrl");
//        String ReturnUrl = maps.get("ReturnUrl");
//        // 后台回调 
//        if (!StringUtils.isEmpty(NotifyUrl)) {
//            alipayRequest.setNotifyUrl(NotifyUrl);
//            // bizContent 中不需要 公共参数 
//            maps.remove("NotifyUrl");
//        }
//        // 页面回调 
//        if (!StringUtils.isEmpty(ReturnUrl)) {
//            alipayRequest.setReturnUrl(ReturnUrl);
//            // bizContent 中不需要 公共参数 
//            maps.remove("ReturnUrl");
//        }
//        String bizCon = JSON.toJSONString(maps);
//        System.out.println("bizCon :" + bizCon);
//        alipayRequest.setBizContent(bizCon);
//        String form = "";
//        try {
//            form = AlipayClientFactory.getAlipayClientInstance().pageExecute(alipayRequest).getBody();
//        } catch (AlipayApiException e) {
//            form = "err";
//            e.printStackTrace();
//        } // 调用SDK生成表单 
//        return form;
//    }
//
//    // 查询订单状态 
//    public AlipayTradeQueryResponse query(String appAuthToken, String bizContent) throws AlipayApiException {
//        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(bizContent);
//        return AlipayClientFactory.getAlipayClientInstance().execute(request);
//    }
//
//    // 条码支付 
//    public AlipayTradePayResponse pay(String appAuthToken, String bizContent) throws AlipayApiException {
//        AlipayTradePayRequest request = new AlipayTradePayRequest();
//        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(bizContent);
//        return AlipayClientFactory.getAlipayClientInstance().execute(request);
//    }
//
//    // 扫码支付 
//    public AlipayTradePrecreateResponse precreate(String appAuthToken, String bizContent) throws AlipayApiException {
//        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(bizContent);
//        return AlipayClientFactory.getAlipayClientInstance().execute(request);
//    }
//
//    // 订单撤销 
//    public AlipayTradeCancelResponse cancel(String appAuthToken, String bizContent) throws AlipayApiException {
//        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
//        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(bizContent);
//        return AlipayClientFactory.getAlipayClientInstance().execute(request);
//    }
//
//    // 申请退款 
//    public AlipayTradeRefundResponse refund(String appAuthToken, String bizContent) throws AlipayApiException {
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//        request.putOtherTextParam("app_auth_token", appAuthToken);
//        request.setBizContent(bizContent);
//        return AlipayClientFactory.getAlipayClientInstance().execute(request);
//    }
//
//    @SuppressWarnings("unused")
//    public static void main(String[] args) {
//        AlipayClientFactory c = new AlipayClientFactory();
//        try {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("out_trade_no", "20160914113218");
//            String bizContent = JSON.toJSONString(map);
//            System.err.println(bizContent);
//            AlipayTradeQueryResponse rp = c.query(null, bizContent);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//    }
}
