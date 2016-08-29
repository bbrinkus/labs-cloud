package com.brinkus.labs.cloud.service.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Configuration
@EnableSwagger2
@Profile({ "!it" })
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

    @Autowired
    ApiInfo apiInfo;

    public SwaggerConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getName());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(excludeErrorController())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo);
    }

    @Bean
    public ApiInfo getApiInfo(SwaggerConfigBean configBean) {
        return new ApiInfo(configBean.getTitle(),
                           configBean.getDescription(),
                           configBean.getVersion(),
                           configBean.getTermsOfServiceUrl(),
                           new Contact(configBean.getContactName(),
                                       configBean.getContactUrl(),
                                       configBean.getContactEmail()),
                           configBean.getLicense(),
                           configBean.getLicenseUrl());
    }

    private Predicate<RequestHandler> excludeErrorController() {
        return Predicates.not(RequestHandlerSelectors.basePackage("org.springframework"));
    }
}