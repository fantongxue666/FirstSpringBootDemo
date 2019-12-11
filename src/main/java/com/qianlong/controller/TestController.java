package com.qianlong.controller;

import com.qianlong.exception.UserNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Slf4j
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

    /**
     * @title
     * @description get请求   getForObject用法
     * @author FanJiangFeng
     * @updateTime 2019/12/9 15:32
     * @throws
     */
    @RequestMapping(value = "demoTest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String demoTest(@RequestParam String name,@RequestParam String email){
        System.out.println(name);
        System.out.println(email);
        return "success";
    }

    /**
     * @title
     * @description get请求  getForEntity用法
     * @author FanJiangFeng
     * @updateTime 2019/12/11 16:19
     * @throws
     */
    @GetMapping(value = "demoTest1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String demoTest1(@RequestParam String name, @RequestParam String email, @RequestHeader String phone){
        System.out.println(name);
        System.out.println(email);
        System.out.println(phone);
        return "success";
    }

    /**
     * @title
     * @description post请求
     * @author FanJiangFeng
     * @updateTime 2019/12/11 16:39
     * @throws
     */
    @RequestMapping(value = "demoTest2", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String demoTest2(@RequestBody User user, @RequestHeader String phone){
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(phone);
        return "success";
    }
}
