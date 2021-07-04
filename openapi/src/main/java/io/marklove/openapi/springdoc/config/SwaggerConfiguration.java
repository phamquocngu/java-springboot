package io.marklove.openapi.springdoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi groupOpenApi() {
        String paths[] = {"/api/book/**"};
        String packagesToscan[] = {"io.marklove.openapi.springdoc.controller"};
        return GroupedOpenApi.builder()
                .group("groups")
                .pathsToMatch(paths)
                .packagesToScan(packagesToscan)
                .addOpenApiCustomiser(serverOpenApiCustomiser())
                .build();
    }

    public OpenApiCustomiser serverOpenApiCustomiser() {
        Server server = new Server().url("http://marklove.io").description("myserver1");
        List<Server> servers = new ArrayList<>();
        servers.add(server);
        return openApi -> openApi.setServers(servers);
    }
}
