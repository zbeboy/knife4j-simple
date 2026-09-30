package io.github.zbeboy.knife4j.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Replace swagger 2 annotations with swagger 3 annotations (it is already included with springdoc-openapi-ui dependency). Package for swagger 3 annotations is io.swagger.v3.oas.annotations.
 *
 * @Api → @Tag
 * @ApiIgnore → @Parameter(hidden = true) or @Operation(hidden = true) or @Hidden
 * @ApiImplicitParam → @Parameter
 * @ApiImplicitParams → @Parameters
 * @Schema → @Schema
 * @Schema(allowEmptyValue = true) → @Schema(nullable = true)
 * @Schema → @Schema
 * @Operation(summary = "foo", notes = "bar") → @Operation(summary = "foo", description = "bar")
 * @ApiParam → @Parameter
 * @ApiResponse(code = 404, message = "foo") → @ApiResponse(responseCode = "404", description = "foo")
 */
@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring boot2")
                        .description("Spring boot2 API")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
