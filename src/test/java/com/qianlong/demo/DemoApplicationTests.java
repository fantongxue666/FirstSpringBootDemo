package com.qianlong.demo;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * springboot单元测试
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void contextLoads() throws FileNotFoundException {
        File file=new File("D://文件笔记//image//1571884758247.png");
        //文件名
        String fileName=file.getName();
        //后缀名
        String extName=fileName.substring(fileName.lastIndexOf(".")+1);
        //创建流
        FileInputStream fileInputStream=new FileInputStream(file);
        //四个参数（输入流，文件大小，后缀名，null）,返回一个路径
        StorePath storePath = fastFileStorageClient.uploadFile(fileInputStream, file.length(), extName, null);
        //不同路径
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getGroup());
    }
}
