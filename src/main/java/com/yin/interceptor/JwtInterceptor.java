package com.yin.interceptor;

import com.yin.exception.NotLoginException;
import com.yin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new NotLoginException(502,"无token，请重新登录");
        }
        else {
            try{
                Claims claims =JwtUtils.parseJwt(token);
                Integer id = claims.get("uid",Integer.class);
                String  name = claims.get("name",String.class);
                request.setAttribute("uid",id);
                request.setAttribute("name",name);
                return true;
            }
            catch (Exception ex){
                throw new NotLoginException(502,"登录信息过期");
            }
        }
    }
}
