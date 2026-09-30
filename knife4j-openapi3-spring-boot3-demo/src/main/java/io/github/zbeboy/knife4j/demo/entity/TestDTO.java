package io.github.zbeboy.knife4j.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "测试DTO")
public class TestDTO {

    @Schema(description = "消息")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
