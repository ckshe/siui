package com.linln;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class PicLocalConfig implements WebMvcConfigurer {

    /**
     * 虚拟路径配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/document/**").addResourceLocations(" file:///C:/chaosheng_file/signature/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
