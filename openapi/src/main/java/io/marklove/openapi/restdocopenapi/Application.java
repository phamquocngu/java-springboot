package io.marklove.openapi.restdocopenapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI().info(new Info().title("Foobar API")
                .version(appVersion)
                .description("This is a sample Foobar server created using springdocs - a library for OpenAPI 3 with spring boot.")
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi fooOpenApi() {
        String paths[] = {"/foo/**"};
        String packagesToscan[] = {"io.marklove.openapi.restdocopenapi"};
        return GroupedOpenApi.builder()
                .group("foo")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }

    @Bean
    public GroupedOpenApi fooBarOpenApi() {
        String paths[] = {"/foobar/**"};
        String packagesToscan[] = {"io.marklove.openapi.restdocopenapi.springdoc"};
        return GroupedOpenApi.builder()
                .group("foobar")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }
}