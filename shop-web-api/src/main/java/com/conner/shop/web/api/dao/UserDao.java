package com.conner.shop.web.api.dao;

import com.conner.shop.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserDao {

    /**
     * 通过 id 获取用户
     * @param id
     * @return User
     */
    User getUserById(String id);

    /**
     * 通过 phone 获取用户
     * @param phone
     * @return User
     */
    User getUserByPhone(String phone);

    /**
     * 通过 name 获取用户信息
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 获取全部用户信息
     * @return
     */
    List<User> getPageUsers(Map<String, Object> params);

    /**
     * 保存用户信息
     * @param user
     */
    void save(User user);

    /**
     * 根据 名称 計算總條數
     * @return
     */
    int countAll(Map<String, Object> countParams);

    /**
     * 通過 id 刪除用戶
     * @param id
     */
    void removeById(String id);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);
}
