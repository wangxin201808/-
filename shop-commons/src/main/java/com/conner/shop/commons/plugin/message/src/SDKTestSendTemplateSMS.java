package com.conner.shop.commons.plugin.message.src;

import com.conner.shop.commons.plugin.message.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

public class SDKTestSendTemplateSMS {
    /**
     * 发送验证码
     * @param phone 手机号
     * @param validator 验证码
     */
    public static void sendSMS(String phone, String validator) {
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        //初始化服务器地址和端口
        restAPI.init("app.cloopen.com", "8883");

        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN
        restAPI.setAccount("8aaf070866e2e5b50166e6ef69880373", "5f9f4061a4b143c5a98943167893648e");

        // 初始化应用ID
        restAPI.setAppId("8aaf070866e2e5b50166e6ef69e1037a");

        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。
        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{validator,"1"});

        System.out.println("SDKTestGetSubAccounts result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
    }
}
