package com.qianlong.controller;

import com.qianlong.exception.UserNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class TestController {
    /**
     * localhost:8080默认访问路径
     */
    @RequestMapping("/")
    public String lo(HttpSession session){
        return "index";
    }

    @RequestMapping("/isUser")
    @ResponseBody
    public String isUser(@RequestParam("id") String id){
        if("aaa".equals(id)){
            throw new UserNotExistException();
        }
        return "Hello";
    }
}
