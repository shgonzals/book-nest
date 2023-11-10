package com.shgonzals.booknest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
							  .title("Book Nest API")
							  .version("1.0.0"))
				.addSecurityItem(new SecurityRequirement().addList("jwtToken"))
				.components(new io.swagger.v3.oas.models.Components()
									.addSecuritySchemes("jwtToken", new SecurityScheme()
											.type(SecurityScheme.Type.HTTP)
											.scheme("bearer")
											.bearerFormat("JWT")));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
							 .group("public-apis")
							 .pathsToMatch("/**")
							 .pathsToExclude("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**")
							 .build();
	}

}
