package com.qianlong.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {
    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
      Object user=request.getSession().getAttribute("account");
      if(user==null){
          request.getRequestDispatcher("/demo/login").forward(request,response);
          return false;
      }

        return true;
    }*/
}
