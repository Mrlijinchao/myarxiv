package com.myarxiv.myarxiv;

import com.myarxiv.myarxiv.common.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.myarxiv.myarxiv.mapper")
@ServletComponentScan
@SpringBootApplication
public class MyarxivApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MyarxivApplication.class, args);
        SpringUtils.set(context);
    }

}
