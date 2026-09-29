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


package io.github.zbeboy.knife4j.spring.extension;

import io.github.zbeboy.knife4j.spring.annotations.ApiOperationSupport;
import io.github.zbeboy.knife4j.spring.annotations.ApiSupport;
import io.github.zbeboy.knife4j.spring.configuration.ExtensionsConstants;
import io.github.zbeboy.knife4j.spring.util.ExtensionUtils;
import io.github.zbeboy.knife4j.spring.util.StrUtil;
import io.swagger.v3.oas.models.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * @author <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2023/2/26 12:14
 * @since knife4j
 */
public class Knife4jOperationCustomizer implements GlobalOperationCustomizer {

    private static final Logger log = LoggerFactory.getLogger(Knife4jOperationCustomizer.class);

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 解析支持作者、接口排序
        // https://gitee.com/xiaoym/knife4j/issues/I6FB9I
        ApiOperationSupport operationSupport = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ApiOperationSupport.class);
        if (operationSupport != null) {
            String author = ExtensionUtils.getAuthors(operationSupport);
            if (StrUtil.isNotBlank(author)) {
                operation.addExtension(ExtensionsConstants.EXTENSION_AUTHOR, author);
            }
            if (operationSupport.order() != 0) {
                operation.addExtension(ExtensionsConstants.EXTENSION_ORDER, operationSupport.order());
            }
        } else {
            // 如果方法级别不存在，再找一次class级别的
            ApiSupport apiSupport = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiSupport.class);
            if (apiSupport != null) {
                String author = ExtensionUtils.getAuthor(apiSupport);
                if (StrUtil.isNotBlank(author)) {
                    operation.addExtension(ExtensionsConstants.EXTENSION_AUTHOR, author);
                }
                if (apiSupport.order() != 0) {
                    operation.addExtension(ExtensionsConstants.EXTENSION_ORDER, apiSupport.order());
                }
            }
        }
        return operation;
    }
}
