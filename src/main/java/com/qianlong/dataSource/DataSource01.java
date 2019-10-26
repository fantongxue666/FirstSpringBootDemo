package com.qianlong.dataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@ComponentScan//注解到springboot容器中
@MapperScan(basePackages = "com.qianlong.test1.mapper",sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSource01 {
    /**
     * 返回test1数据库的数据源
     */
    @Bean(name = "test1DataSource")
    @Primary//主数据源，一个应用只能有一个主数据源，并且一定要声明
    @ConfigurationProperties(prefix = "spring.datasource.test1")//这里根据前缀去和application.properties配置去匹配
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}
