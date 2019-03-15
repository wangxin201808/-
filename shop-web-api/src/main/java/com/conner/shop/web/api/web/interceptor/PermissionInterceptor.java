package com.conner.shop.web.api.web.interceptor;

import com.conner.shop.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.conner.shop.commons.constant.ConstantUtils.SESSION_USER;

public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null && modelAndView.getViewName() != null && modelAndView.getViewName().endsWith("login") ) {
            User user = (User) httpServletRequest.getSession().getAttribute(SESSION_USER);
            if (user != null) {
                httpServletResponse.sendRedirect("/main");
            }

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
