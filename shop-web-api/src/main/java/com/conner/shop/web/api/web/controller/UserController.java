package com.conner.shop.web.api.web.controller;

import com.conner.shop.commons.dto.BaseResult;
import com.conner.shop.domain.User;
import com.conner.shop.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    BaseResult baseResult;

    /**
     * 分页获取页面标签
     * @param request
     * @return
     */
    @RequestMapping(value = "listpage", method = RequestMethod.GET)
    public Map<String, Object> getListPage(HttpServletRequest request) {
        List<User> users = null;

        String page = request.getParameter("page");
        String length = request.getParameter("length");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        Map<String, Object> listPage = userService.getListPage(Integer.parseInt(page), Integer.parseInt(length), name, phone);

        return listPage;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseResult save(@ModelAttribute User user) {

        return userService.save(user);
    }

    /**
     * 批量刪除
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchremove", method = RequestMethod.POST)
    public BaseResult batchRemove(String ids){
        String[] userIds = ids.split(",");
//        System.out.println(userIds.length);
        return userService.batchRemove(userIds);
    }

    /**
     * 通过 id 获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getinfo", method = RequestMethod.POST)
    public BaseResult getUserInfoByID (String id) {
        return userService.getUser(id);
    }
}
