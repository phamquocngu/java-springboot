package io.marklove.spring.security.jwt.configurations;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngupq
 */
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI().info(new Info().title("Marklove APIs")
                .version(appVersion)
                .description("This is a sample server created using springdocs - a library for OpenAPI 3 with spring boot.")
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi securityOpenApi() {
        String paths[] = { ApiUrls.AUTH + ApiUrls.PATTERN_ALL,
                ApiUrls.SIGN_UP + ApiUrls.PATTERN_ALL,
                ApiUrls.RESET_PASS + ApiUrls.PATTERN_ALL,
                ApiUrls.USER + ApiUrls.PATTERN_ALL};

        String packagesToscan[] = {"io.marklove.spring.security.jwt.controllers.security"};

        return GroupedOpenApi.builder()
                .group("security")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }

    @Bean
    public GroupedOpenApi businessOpenApi() {
        String paths[] = {"/api/test/**"};
        String packagesToscan[] = {"io.marklove.spring.security.jwt.controllers"};
        return GroupedOpenApi.builder()
                .group("business")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .build();
    }
}
