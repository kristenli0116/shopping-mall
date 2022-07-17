package com.study.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @auther lkx
 * @create 2022-04-28 15:32
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * swagger会帮助我们生成接口文档
     * 1.配置生成的文档信息
     * 2.配置生成的规则
     * @return
     */

    /*Docket封装接口信息文档*/
    @Bean
    public Docket getDocket(){
        //指定文档风格
        ApiInfoBuilder apiInfoBuilder =new ApiInfoBuilder();
        apiInfoBuilder.title("《**商城》后端接口说明")
                .description("此文档详细说明了商城的后端接口规范...")
                .version("version 2.0.1")
                .contact(new Contact("kirsten_li","www.li.com","li@qq.com"));
        ApiInfo apiInfo = apiInfoBuilder.build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.study.api.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;


    }
}
