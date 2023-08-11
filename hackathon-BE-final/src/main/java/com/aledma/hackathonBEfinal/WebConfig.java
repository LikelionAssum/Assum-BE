package com.aledma.hackathonBEfinal;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
CORS(Cross-Origin Resource Sharing) : 프론트와 백엔드 간의 접근 권한이 달라서 발생하는 보안 메커니즘
이 파일은 CORS에러 해결을 위해 작성됨.
수정이 필요함
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // allowedOrigins에 프론트 서버 도메인이 들어가면 될거 같은 느낌?
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("https://localhost:300-")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true);
    }
}
