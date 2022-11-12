package com.ryouonritsu.aadp.config

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author ryouonritsu
 */
@Configuration
@EnableOpenApi
class SwaggerConfig {
    @Bean
    fun createRestApi(): Docket = Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo()).enable(true)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Tag::class.java))
        .paths(PathSelectors.any())
        .build()

    fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("InkBook Api")
        .description("InkBook backend api documentation")
        .termsOfServiceUrl("")
        .version("1.0")
        .build()
}