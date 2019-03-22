package com.conner.shop.web.api.web.controller;

import com.conner.shop.commons.constant.ConstantUtils;
import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.User;
import com.conner.shop.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    Map<String, String> phoneAndValidator = new HashMap<>();

    /**
     * 用户名密码登录
     * @param name
     * @param password
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "password", required = true) String password, HttpServletRequest httpServletRequest) {
        User user = userService.login(name, password);
        if (user != null) {
            httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER, user);
            return BaseResult.success("成功", user);
        }

        return BaseResult.fail("用户名或密码错误");
    }

    /**
     * 用户退出
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public BaseResult logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return BaseResult.success();
    }

    /**
     * 发送手机验证码
     * @param phone
     */
    @RequestMapping(value = "sendsms", method = RequestMethod.GET)
    public BaseResult sendSMS(String phone){
        if (phone != "") {
            // 产生验证码
            Random random = new Random(System.currentTimeMillis() + 19960716);
            String val = "" + random.nextInt();
            String substring = val.substring(val.length() - 6, val.length());
            System.out.println(substring);

            phoneAndValidator.put(phone, substring);

            // 发送验证码
//        SDKTestSendTemplateSMS.sendSMS(phone, substring);
            return BaseResult.success();
        }


        return BaseResult.fail("请输入手机号");

    }

    /**
     * 通过手机号和验证码登录
     * @param phone
     * @param validator
     * @return
     */
    @RequestMapping(value = "loginbyphone", method = RequestMethod.POST)
    public BaseResult loginByPhone (String phone, String validator) {
        String trueValidator = phoneAndValidator.get(phone);
        User user = userService.loginByPhone(phone, validator, trueValidator);
        if (user != null) {
            return BaseResult.success("登录成功", user);
        }
        return BaseResult.fail("手机号未注册或验证码错误");
    }

    /**
     * 判断手机号是否注册
     * @param phone
     * @param validator
     * @return
     */
    @RequestMapping(value = "testphone", method = RequestMethod.POST)
    public BaseResult testPhone (String phone, String validator) {
        if (phoneAndValidator.get(phone).equals(validator)){
            return userService.testPhone(phone);
        }
        return BaseResult.fail("信息输入错误");
    }

    /**
     * 手机号注册
     * @param phone
     * @param secret
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult register(String phone, String secret) {
        return userService.register(phone, secret);

    }

    /**
     * 根据用户id 获取用户信息，并修改密码
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "resetpwd", method = RequestMethod.POST)
    public BaseResult resetPassword(String phone, String password, String validator) {
        if (phoneAndValidator.get(phone).equals(validator)){
            return userService.resetPassword(phone, password);
        }
        return BaseResult.fail("验证码错误");
    }

}
