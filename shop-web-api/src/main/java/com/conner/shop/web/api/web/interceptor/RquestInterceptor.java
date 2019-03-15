package com.conner.shop.web.api.web.interceptor;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RquestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("beforeAction");

        // 当Access-Control-Allow-Credentials设为true的时候，Access-Control-Allow-Origin不能设为星号
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 可以指定特定的ip:port
        //response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:9006, http://127.0.0.1:8080");
        response.setHeader("Access-Control-Allow-Origin", "*");// *
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type, xxxx");// * ;
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,PUT,OPTIONS,DELETE,HEAD");
        response.setHeader("Content-Type", "application/json");
        String method= request.getMethod();
        System.out.println(method);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
