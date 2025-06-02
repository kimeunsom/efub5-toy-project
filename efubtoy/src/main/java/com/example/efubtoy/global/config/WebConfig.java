package com.example.efubtoy.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override // 이제 이 @Override는 WebMvcConfigurer의 addCorsMappings를 정확히 오버라이드합니다.
    public void addCorsMappings(CorsRegistry registry) { // ⭐ configureCors -> addCorsMappings 로 변경
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:51473", "https://busanlottegiants.p-e.kr")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}