package com.qianlong.demo;

import com.qianlong.bean.Person;
import com.qianlong.controller.SelectController;
import com.qianlong.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springboot单元测试
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    //记录器
   //Logger logger= LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        //日志的级别
        //由低到高
        //可以调整输出的日志级别，日志就只会在这个级别以偶的高级别生效
        /*logger.trace("这是trace日志");
        logger.debug("这是debug日志");
        //springboot默认给我们使用的是info级别的日志，没有指定级别的就使用springboot默认级别的（info）
        logger.info("这是info日志");
        logger.warn("这是warn日志");
        logger.error("这是error日志");*/

    }
}
