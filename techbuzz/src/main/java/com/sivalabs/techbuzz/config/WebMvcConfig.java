package com.sivalabs.techbuzz.config;

import com.sivalabs.techbuzz.config.argresolvers.CurrentUserArgumentResolver;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .resourceChain(true)
                .addResolver(
                        new PathResourceResolver() {
                            @Override
                            protected Resource getResource(String resourcePath, Resource location)
                                    throws IOException {
                                Resource requestedResource = location.createRelative(resourcePath);

                                return requestedResource.exists() && requestedResource.isReadable()
                                        ? requestedResource
                                        : new ClassPathResource("/public/index.html");
                            }
                        });
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }
}
