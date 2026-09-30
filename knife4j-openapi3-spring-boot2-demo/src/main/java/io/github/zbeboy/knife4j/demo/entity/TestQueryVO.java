package io.github.zbeboy.knife4j.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "测试查询VO")
public class TestQueryVO {

    @Schema(description = "名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestQueryVO{" +
                "name='" + name + '\'' +
                '}';
    }
}
