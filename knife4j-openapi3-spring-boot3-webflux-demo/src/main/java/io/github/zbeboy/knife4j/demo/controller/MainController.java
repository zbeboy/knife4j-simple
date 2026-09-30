package io.github.zbeboy.knife4j.demo.controller;

import io.github.zbeboy.knife4j.demo.entity.TestDTO;
import io.github.zbeboy.knife4j.demo.entity.TestQueryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "测试环境")
@RestController
public class MainController {

    /**
     * @param mo 数据
     * @return 结果
     */
    @Operation(summary = "1.01 查询")
    @PostMapping("/test")
    public Mono<TestDTO> test(@RequestBody Mono<TestQueryVO> mo) {
        return mo.flatMap(vo -> {
            TestDTO dto = new TestDTO();
            dto.setMsg("Hi! " + vo.getName());
            return Mono.just(dto);
        });
    }
}
