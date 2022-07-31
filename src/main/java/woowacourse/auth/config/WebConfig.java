package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.support.JwtTokenProvider;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginInterceptor loginInterceptor;

    public WebConfig(JwtTokenProvider jwtTokenProvider, final LoginInterceptor loginInterceptor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/users/me/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginCustomerResolver());
    }

    @Bean
    public LoginCustomerResolver loginCustomerResolver() {
        return new LoginCustomerResolver(jwtTokenProvider);
    }

//    @Bean
//    public FilterRegistrationBean loginFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new
//                FilterRegistrationBean<>();
//
//        filterRegistrationBean.setFilter(new LoginFilter(jwtTokenProvider));
//        filterRegistrationBean.setOrder(1);
//        filterRegistrationBean.addUrlPatterns("/users/me/*");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean customFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//
//        filterRegistrationBean.setFilter(new CustomFilter());
//        filterRegistrationBean.setOrder(2);
//        filterRegistrationBean.addUrlPatterns("*");
//
//        return filterRegistrationBean;
//    }
}
