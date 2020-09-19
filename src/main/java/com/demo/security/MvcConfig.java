package com.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig  implements WebMvcConfigurer {
//it mapping the page like controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/admin/admin_detail").setViewName("admin_detail");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/user&admin").setViewName("user&admin");
        registry.addViewController("/general").setViewName("general");
        registry.addViewController("/403").setViewName("403");


    }
}
