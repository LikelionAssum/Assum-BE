package com.aledma.hackathonBEfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig { //extends WebSecurityConfigurerAdapter, deprecated?

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/**")
                //.requestMatchers(new AntPathRequestMatcher("/**"))
                .permitAll()
                .and()
                // CORS 설정
                .cors(c -> {
                    CorsConfigurationSource source = request -> {
                        // CORS 허용 패턴
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(
                                List.of("*")
                        );
                        config.setAllowedMethods(
                                List.of("*")
                        );
                        config.setAllowedHeaders(
                                List.of("*")
                        );

                        return config;
                    };
                    c.configurationSource(source);
                });
        return http.build();
    }
}

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll() // 로그인 엔드포인트는 모든 사용자에게 허용
//                .antMatchers("/api/my-feature").hasRole("USER") // 다른 기능은 ROLE_USER 역할이 필요, 경로를 바꿔야 할 듯
//                .and()
//                .oauth2Login(); // OAuth2 로그인 설정
//    }