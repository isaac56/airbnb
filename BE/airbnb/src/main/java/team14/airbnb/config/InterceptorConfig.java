package team14.airbnb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team14.airbnb.interceptor.CertificationInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private HandlerInterceptor interceptor;

    @Autowired
    public InterceptorConfig(CertificationInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/login/**");
    }
}
