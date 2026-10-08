/*
 * Copyright © 2017-2023 Knife4j(xiaoymin@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.github.zbeboy.knife4j.spring.reactor.configuration;

import io.github.zbeboy.knife4j.spring.reactor.extension.Knife4jOpenApiCustomizer;
import io.github.zbeboy.knife4j.spring.reactor.extension.Knife4jOperationCustomizer;
import io.github.zbeboy.knife4j.spring.reactor.filter.AbstractSecurityFilter;
import io.github.zbeboy.knife4j.spring.reactor.filter.ProductionSecurityFilter;
import io.github.zbeboy.knife4j.spring.reactor.filter.SecurityBasicAuthFilter;
import io.github.zbeboy.knife4j.spring.reactor.util.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/***
 * Knife4j 基础自动配置类
 * @since 2.0.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2019/08/28 21:08
 */
@Configuration
@EnableConfigurationProperties({Knife4jProperties.class, Knife4jHttpBasic.class, Knife4jSetting.class})
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true")
public class Knife4jAutoConfiguration {

    @Autowired
    private Environment environment;
    Logger logger = LoggerFactory.getLogger(Knife4jAutoConfiguration.class);

    /**
     * 增强自定义配置
     *
     * @param knife4jProperties 配置
     * @param docProperties     doc配置
     * @return 自定义
     */
    @Bean
    @ConditionalOnMissingBean
    public Knife4jOpenApiCustomizer knife4jOpenApiCustomizer(Knife4jProperties knife4jProperties,
                                                             SpringDocConfigProperties docProperties) {
        logger.debug("Register Knife4jOpenApiCustomizer");
        return new Knife4jOpenApiCustomizer(knife4jProperties, docProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public Knife4jOperationCustomizer knife4jOperationCustomizer() {
        return new Knife4jOperationCustomizer();
    }

    /**
     * 配置Cors
     *
     * @return 过滤器
     * @since 2.0.4
     */
    @Bean("knife4jCorsFilter")
    @ConditionalOnMissingBean(CorsWebFilter.class)
    @ConditionalOnProperty(name = "knife4j.cors", havingValue = "true")
    public CorsWebFilter corsWebFilter() {
        logger.info("init CorsWebFilter...");
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setMaxAge(10000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }

    /**
     * Security with Basic Http
     *
     * @param knife4jProperties Basic Properties
     * @return BasicAuthFilter
     */
    @Bean
    @Order
    @ConditionalOnMissingBean(SecurityBasicAuthFilter.class)
    @ConditionalOnExpression("${knife4j.basic.enable:true}")
    public SecurityBasicAuthFilter securityBasicAuthFilter(Knife4jProperties knife4jProperties) {
        SecurityBasicAuthFilter authFilter = new SecurityBasicAuthFilter();
        if (knife4jProperties == null) {
            authFilter.setEnableBasicAuth(EnvironmentUtils.resolveBool(environment, "knife4j.basic.enable", Boolean.FALSE));
            authFilter.setUserName(EnvironmentUtils.resolveString(environment, "knife4j.basic.username", GlobalConstants.BASIC_DEFAULT_USERNAME));
            authFilter.setPassword(EnvironmentUtils.resolveString(environment, "knife4j.basic.password", GlobalConstants.BASIC_DEFAULT_PASSWORD));
        } else {
            // 判断非空
            if (knife4jProperties.getBasic() == null) {
                authFilter.setEnableBasicAuth(Boolean.FALSE);
                authFilter.setUserName(GlobalConstants.BASIC_DEFAULT_USERNAME);
                authFilter.setPassword(GlobalConstants.BASIC_DEFAULT_PASSWORD);
            } else {
                authFilter.setEnableBasicAuth(knife4jProperties.getBasic().isEnable());
                authFilter.setUserName(knife4jProperties.getBasic().getUsername());
                authFilter.setPassword(knife4jProperties.getBasic().getPassword());
                authFilter.addRule(knife4jProperties.getBasic().getInclude());
            }
        }
        return authFilter;
    }

    @Bean
    @Order(AbstractSecurityFilter.SPRING_FILTER_ORDER - 1)
    @ConditionalOnMissingBean(ProductionSecurityFilter.class)
    @ConditionalOnProperty(name = "knife4j.production", havingValue = "true")
    public ProductionSecurityFilter productionSecurityFilter(Knife4jProperties knife4jProperties) {
        boolean prod = false;
        ProductionSecurityFilter p;
        if (knife4jProperties == null) {
            if (environment != null) {
                String prodStr = environment.getProperty("knife4j.production");
                if (logger.isDebugEnabled()) {
                    logger.debug("swagger.production:{}", prodStr);
                }
                prod = Boolean.parseBoolean(prodStr);
            }
            p = new ProductionSecurityFilter(prod);
        } else {
            p = new ProductionSecurityFilter(knife4jProperties.isProduction());
        }
        return p;
    }

}
