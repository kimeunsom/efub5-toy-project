package com.example.efubtoy.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureCors(CorsRegistry registry) {
        registry.addMapping("/**")
                // ⭐⭐ 기존 http://localhost:5173 에 더하여 배포된 백엔드 도메인을 추가합니다. ⭐⭐
                .allowedOrigins("http://localhost:5173", "https://busanlottegiants.p-e.kr")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}