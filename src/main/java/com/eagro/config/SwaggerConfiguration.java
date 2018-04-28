package com.eagro.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mangofactory.swagger.plugin.EnableSwagger;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableSwagger
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("ccmtrack").select()
        		.apis(RequestHandlerSelectors.basePackage("com.cpa.ccm.web.rest")).paths(PathSelectors.ant("/api/rest/*")).build().apiInfo(apiInfo()).useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build(), new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build()));
    }

    private ApiInfo apiInfo() {
    	ApiInfo apiInfo = new ApiInfo("EAGRO BUDDY API", "API List for Layout Visualization", "1.0.0", "Terms of Service", "Veera", "License", "License URL");
        return apiInfo;
    }
}
