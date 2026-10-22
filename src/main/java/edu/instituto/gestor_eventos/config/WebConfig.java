package edu.instituto.gestor_eventos.config;

import edu.instituto.gestor_eventos.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/eventos", "/eventos/**", "/preferencia")
                .excludePathPatterns("/login", "/registro", "/css/**", "/js/**", "/error");
    }
}