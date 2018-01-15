package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 将配置的路径添加为静态资源文件
 * Created by yangting on 2018/1/6.
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    @Value("${uploadPath}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!basePath.endsWith("/")){
            basePath = basePath + "/";
        }
        String path = "file:" + basePath + "fmsf/";
        registry.addResourceHandler("/fmsf/**").addResourceLocations(path);
        super.addResourceHandlers(registry);
    }

}
