package com.pegoopik.sudoku.web;

import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder() //
                .displayRequestDuration(true) //
                .validatorUrl(StringUtils.EMPTY) // Disable the validator to avoid "Error" at the bottom of the Swagger UI page
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Прогулка по задаче Судоку вместе с Java")
                .description("Решаем / анализируем / генерируем и всякое такое")
                .contact(new Contact("Eugene Pegoopik Krasovskiy", "", "zhudzek@gmail.com"))
                .version(getBuildVersion())
                .build();
    }

    @SneakyThrows
    private String getBuildVersion() {
        try (InputStream stream = getClass()
                .getResourceAsStream("/META-INF/build-info.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties.getProperty("build.version");
        } catch (Exception e) {
            return "";
        }
    }

}
