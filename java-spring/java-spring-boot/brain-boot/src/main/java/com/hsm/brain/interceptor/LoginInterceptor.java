package com.hsm.brain.interceptor;

import com.hsm.brain.config.UserContext;
import com.hsm.brain.model.po.UserPO;
import com.hsm.brain.service.IUserService;
import com.hsm.brain.utils.JWTUtils;
import com.hsm.brain.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname LoginInterceptor
 * @Description 登录拦截器
 * @Date 2021/8/4 17:47
 * @Created by huangsm
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isNotEmpty(authorization)){
            try{
                UserPO user= userService.parseToken(authorization);
                UserContext.setUser(user);
            }catch (Exception e){
                log.error("解析token异常");
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
