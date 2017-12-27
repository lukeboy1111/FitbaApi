package com.lukeyboy1111.fitba.apis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lukeyboy1111.fitba.apis.configuration.constants.APIConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-09T16:11:27.745Z")

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(APIConstants.TITLE)
            .description(APIConstants.DESCRIPTION)
            .version(APIConstants.VERSION)
            .contact(APIConstants.CONTACT)
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.lukeyboy1111.fitba.apis.api"))
                    .build()
                .apiInfo(apiInfo());
    }

}
