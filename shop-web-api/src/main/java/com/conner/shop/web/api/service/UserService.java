package com.conner.shop.web.api.service;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.User;

import java.util.Map;

public interface UserService {
    /**
     * 用户名 密码 登录
     * @param name
     * @param password
     * @return
     */
    User login(String name, String password);

    /**
     * 获取所有用户信息
     * @return
     */
    Map<String, Object> getListPage(int page, int length, String name, String phone);

    /**
     * 新增用户
     * @param user
     * @return
     */
    BaseResult save(User user);

    /**
     * 批量刪除
     * @param userIds
     */
    BaseResult batchRemove(String[] userIds);

    /**
     * 手机号登录
     * @param phone
     * @return
     */
    User loginByPhone(String phone, String validator, String trueValidator);

    /**
     * 手机号 注册
     * @param phone
     * @param secret
     * @return
     */
    BaseResult register(String phone, String secret);

    /**
     * 测试手机号是否已注册
     * @param phone
     * @return
     */
    BaseResult testPhone(String phone);

    /**
     * 通过 id 获取用户信息
     * @param id
     * @return
     */
    BaseResult getUser(String id);
}
