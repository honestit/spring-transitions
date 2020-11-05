package pl.honestit.demos.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebLayerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Dodanie obsługi zawartości bibliotek webjars do obsługi w formie
        // zasobów statycznych
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");

        // Dla środowisk nie wspierających implementacji Servlets 3.0 będzie to wyglądało
        // następująco:
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
