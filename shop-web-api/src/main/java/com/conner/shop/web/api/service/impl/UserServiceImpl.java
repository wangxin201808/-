package com.conner.shop.web.api.service.impl;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.commons.utils.BeanValidator;
import com.conner.shop.domain.User;
import com.conner.shop.web.api.dao.UserDao;
import com.conner.shop.web.api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String name, String password) {
        User user = null;
        user = userDao.getUserByName(name);
        if (user != null) {
            if (user.getName().equals(name)) {
                password = DigestUtils.md5DigestAsHex(password.getBytes());
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getListPage(int page, int length, String name, String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page*length);
        params.put("length", length);
        params.put("name", name);
        params.put("phone", phone);
        List<User> pageUsers = userDao.getPageUsers(params);
        int size = pageUsers.size();

        Map<String, Object> countParams = new HashMap<>();
        countParams.put("name", name);
        countParams.put("phone", phone);
        Integer count = null;
        if (size == 0) {
            count = 0;
        } else {
            count = userDao.countAll(countParams);
        }




        Map<String, Object> results = new HashMap<>();
        results.put("users", pageUsers);
        results.put("total", count);

        return results;
    }

    @Override
    @Transactional(readOnly = false)
    public BaseResult save(User user) {
        String validator = BeanValidator.validator(user);
        // 验证不通过
        if (validator != null) {
            return BaseResult.fail(validator);
        }
        // 验证通过
        else {

            // 新增用户
            if (user.getId() == null) {
                System.out.println(UUID.randomUUID().toString().replace("-", ""));
                user.setId(UUID.randomUUID().toString().replace("-", ""));
                // 设置默认密码
                user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

                user.setCreated(new Date());

                userDao.save(user);
            }

            // 修改用户
            else {
                // 后台系统没有设置密码
                if(StringUtils.isBlank(user.getPassword())) {
                    User userById = userDao.getUserById(user.getId());
                    user.setPassword(userById.getPassword());
                }
                // 前台系统设置了密码
                else {
                    // 验证密码是否符合规范
                    if (StringUtils.length(user.getPassword()) >= 6 && StringUtils.length(user.getPassword()) < 32) {
                        return BaseResult.fail("密码长度要在6~32位之间");
                    }
                    // 设置密码加密
                    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

                }
                userDao.update(user);
            }

        }

        return BaseResult.success("保存用户成功");
    }

    @Override
    @Transactional(readOnly = false)
    public BaseResult batchRemove(String[] userIds) {
        for (String id: userIds) {
            userDao.removeById(id);
        }
        return BaseResult.success("用戶刪除成功");
    }

    @Override
    public User loginByPhone(String phone, String validator, String trueValidator) {
        User user = null;
        user = userDao.getUserByPhone(phone);
        if (user != null) {
            if (trueValidator.equals(validator)) {
                return user;
            }
        }

        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public BaseResult register(String phone, String secret) {
        String id = UUID.randomUUID().toString().replace("-", "");
        User user = new User();
        user.setCreated(new Date());
        user.setId(id);
        user.setPhone(phone);
        user.setPassword(DigestUtils.md5DigestAsHex(secret.getBytes()) );
        try {
            userDao.save(user);
            return BaseResult.success("注册成功", user.getId());
        }catch (Exception e){
            return BaseResult.fail();
        }

    }

    @Override
    public BaseResult testPhone(String phone) {
        User user = userDao.getUserByPhone(phone);
        if (user != null) {
            return BaseResult.fail("对不起，您的手机号已经注册");
        } else {
            return BaseResult.success("本号码可以注册");
        }
    }

    @Override
    public BaseResult getUser(String id) {
        User user = userDao.getUserById(id);
        if (user != null) {
            return BaseResult.success("成功", user);
        }else {
            return BaseResult.fail("获取用户信息失败");
        }
    }
}
