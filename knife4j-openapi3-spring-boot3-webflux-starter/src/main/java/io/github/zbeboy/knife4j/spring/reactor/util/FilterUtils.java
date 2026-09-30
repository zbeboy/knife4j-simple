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


package io.github.zbeboy.knife4j.spring.reactor.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2023/2/25 18:56
 * @since knife4j
 */
public class FilterUtils {
    
    /**
     * 响应Basic信息
     *
     * @param response 响应
     */
    public static Mono<Void> writeForbiddenCode(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"input Swagger Basic userName & password \"");
        DataBuffer dataBuffer = response.bufferFactory().wrap("You do not have permission to access this resource".getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
    
}
