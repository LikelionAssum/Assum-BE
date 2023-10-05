package com.aledma.hackathonBEfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("assum(어썸) 해커톤 Swagger UI")
                .version("1.0.0")
                .description("해커톤 Swagger")
                .build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(apiInfo()).select()
                // controller 파일들이 있는 경로(패키지?) 설정
                .apis(RequestHandlerSelectors.basePackage("com.aledma.hackathonBEfinal.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        produces.add("text/plain");
        return produces;
    }


}

//package com.aledma.hackathonBEfinal.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.HttpAuthenticationScheme;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//@Configuration
//@EnableWebMvc
//public class SwaggerConfig {
//    private static final String REFERENCE = "Authorization 헤더 값";
//
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("assum(어썸) 해커톤 Swagger UI")
//                .version("1.0.0")
//                .description("해커톤 Swagger")
//                .build();
//
//
//    }

//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .consumes(getConsumeContentTypes())
//                .produces(getProduceContentTypes())
//                .apiInfo(apiInfo()).select()
//                // controller 파일들이 있는 경로(패키지?) 설정
//                .apis(RequestHandlerSelectors.basePackage("com.aledma.hackathonBEfinal.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .useDefaultResponseMessages(false)
//                .securityContexts(List.of(securityContext()))
//                .securitySchemes(List.of(bearerAuthSecurityScheme()));
//    }
//    private Set<String> getConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        consumes.add("application/json;charset=UTF-8");
//        consumes.add("application/x-www-form-urlencoded");
//        return consumes;
//    }
//
//    private Set<String> getProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        produces.add("application/json;charset=UTF-8");
//        produces.add("text/plain");
//        return produces;
//    }
//
//    private SecurityContext securityContext(){
//        return springfox.documentation
//                .spi.service.contexts
//                .SecurityContext
//                .builder()
//                .securityReferences(defaultAuth())
//                .operationSelector(operationContext -> true)
//                .build();
//    }
//
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
//        return List.of(new SecurityReference(REFERENCE, authorizationScopes));
//    }
//
//    private HttpAuthenticationScheme bearerAuthSecurityScheme(){
//        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
//                .name(REFERENCE).build();
//    }
//}
