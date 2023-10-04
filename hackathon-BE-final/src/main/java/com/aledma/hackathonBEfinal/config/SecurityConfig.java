package com.aledma.hackathonBEfinal.config;

import com.aledma.hackathonBEfinal.JWT.JwtAccessDeniedHandler;
import com.aledma.hackathonBEfinal.JWT.JwtAuthenticationEntryPoint;
import com.aledma.hackathonBEfinal.JWT.JwtRequestFilter;
import com.aledma.hackathonBEfinal.JWT.JwtTokenProvider;
import io.swagger.models.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{ //extends WebSecurityConfigurerAdapter, deprecated?

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final String[] URL_TO_PERMIT = {
            "/swagger-ui/index.html",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/index.css",
            "/swagger-ui/favicon-32x32.png",
            "/swagger-ui/favicon-16x16.png",
            "/api-docs/json/swagger-config",
            "/api-docs/json"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeHttpRequests()
                .antMatchers(URL_TO_PERMIT).permitAll()
                .anyRequest().authenticated()
                //.requestMatchers(new AntPathRequestMatcher("/**"))
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

        http
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

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