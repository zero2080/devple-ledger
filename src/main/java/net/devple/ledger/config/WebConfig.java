package net.devple.ledger.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry cors){
        cors.addMapping("/**")  // 모든경로에 CORS 필터 적용
                .allowedOriginPatterns("http://localhost:8080","http://localhost:3000") // 접근 허용 주소
                .allowedMethods("GET","POST","PUT","DELETE","HEADER","OPTIONS") // 허용 메서드
                .allowedHeaders("*")    // 모든 헤더 허용
                .maxAge(1800);          // 연결 유지 시간 (초)
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
            .addResourceLocations("classpath:/static/files/");
    }
}
