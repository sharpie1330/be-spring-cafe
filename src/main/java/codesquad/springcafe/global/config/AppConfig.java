package codesquad.springcafe.global.config;

import codesquad.springcafe.global.interceptor.AfterAuthorizeInterceptor;
import codesquad.springcafe.global.interceptor.AuthenticationInterceptor;
import codesquad.springcafe.global.interceptor.CsrfTokenIntercetor;
import codesquad.springcafe.global.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    // 특정 URL 경로에 대한 요청을 처리하는 컨트롤러를 등록
    // 실제 작업을 수행하는 컨트롤러가 아닌 단순히 특정 뷰로 이동시키는 역할 (라우팅 x)
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE); // 다른 빈들보다 우선순위 위로

        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/join").setViewName("user/form");
        registry.addViewController("/question").setViewName("post/form");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // static 경로의 리소스들은 스프링 인터셉터 거치지 않고 직접 브라우저에 제공
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인증 인터셉터
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/profile/**", "/users/**", "/question/**", "/user/withdraw/**"); // 등록한 경로에 대해 인터셉트

        // CSRF 토큰 인터셉터
        registry.addInterceptor(new CsrfTokenIntercetor())
                .addPathPatterns("/post", "/user/logout", "/question/edit/**", "/question/delete/**", "/user/withdraw/**")
                .excludePathPatterns("/user", "/user/login");

        // 인가 이후 인터셉터
        registry.addInterceptor(new AfterAuthorizeInterceptor())
                .addPathPatterns("/login", "/join");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
