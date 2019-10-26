package com.qianlong.config;


import com.qianlong.controller.TestController;
import com.qianlong.testService.testService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Configuration标注这个类是配置类，就是来替代之前的spring的配置文件
 * 在配置文件中用<bean></bean>标签添加组件
 */
@Configuration
public class MyAppConfig implements WebMvcConfigurer {

    //将方法的返回值添加到容器中，容器中这个组件默认的id就是方法名
   /* @Bean
    public testService testService(){
        System.err.println("配置类@Bean给容器中添加组件了");
        return new testService();
    }*/

   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/demo/login","/demo/denglu");
    }*/
   @Bean
   public LocaleResolver mylocaleResover(){
       return new MylocaleResover();
   }
}
