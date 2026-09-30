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


package io.github.zbeboy.knife4j.spring.reactor.filter;

import io.github.zbeboy.knife4j.spring.reactor.configuration.GlobalConstants;
import io.github.zbeboy.knife4j.spring.reactor.util.FilterUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2023/2/25 19:06
 * @since knife4j
 */
public class SecurityBasicAuthFilter extends AbstractSecurityFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
        if (this.isEnableBasicAuth() && this.match(url)) {

            return exchange.getSession().flatMap(webSession -> {
                Object sessionObject = webSession.getAttribute(GlobalConstants.KNIFE4J_BASIC_AUTH_SESSION);
                String auth = request.getHeaders().getFirst(GlobalConstants.AUTH_HEADER_NAME);
                if (this.tryCommonBasic(url, sessionObject, auth)) {
                    if (sessionObject == null) {
                        webSession.getAttributes().put(GlobalConstants.KNIFE4J_BASIC_AUTH_SESSION, getUserName());
                    }
                    return chain.filter(exchange).doFinally(signal -> {
                        // 后置处理
//                        this.urlFilters = null;
                    });
                } else {
                    return FilterUtils.writeForbiddenCode(response);
                }

            });
        }
        return chain.filter(exchange).doFinally(signal -> {
            // 后置处理
//            this.urlFilters = null;
        });
    }
}
