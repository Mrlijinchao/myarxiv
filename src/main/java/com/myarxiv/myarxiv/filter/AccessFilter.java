//package com.myarxiv.myarxiv.filter;
//
//import com.myarxiv.myarxiv.common.RedisUtils;
//import com.myarxiv.myarxiv.common.ResponseResult;
//import com.myarxiv.myarxiv.common.SpringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@WebFilter(filterName="AccessFilter",urlPatterns="/*")
//public class AccessFilter implements Filter {
//    Logger logger = LoggerFactory.getLogger(AccessFilter.class);
//
//    private RedisUtils redisUtils;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        if(request.getRequestURI().contains("/user") || request.getRequestURI().contains("/submit")){
//
//            if(request.getRequestURI().contains("/login") || request.getRequestURI().contains("/register")){
//                filterChain.doFilter(request, response);
//            }
//
//            //非Spring管理环境，需要手动注入Bean
//            redisUtils = SpringUtils.getBean(RedisUtils.class);
//            /** token+redis验证 */
//            String token = request.getHeader("Authorization");
//            System.out.println("token:"+token);
//            if (!redisUtils.hasKey(token)) {
//                logger.info("请求的用户没带token，或者token已过期");
//                return ;
//            }
//
//            //刷新token有效期
//            redisUtils.expire(token, 1, TimeUnit.HOURS);
//
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//
//    @Override
//    public void destroy() {
//    }
//}