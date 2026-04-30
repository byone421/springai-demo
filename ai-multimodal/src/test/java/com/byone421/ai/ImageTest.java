package com.byone421.ai;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImageTest {

    @Autowired
    private DashScopeImageModel imageModel;


    @Test
    public void test() {
        String prompt = "一只可爱的刺猬在草地上吃苹果，背景是蓝天白云，风格是卡通";
        ImageResponse call = imageModel.call(
                new ImagePrompt(prompt, DashScopeImageOptions.builder().model("wanx2.1-t2i-turbo").build())
        );
        System.out.println(call.getResult().getOutput().getUrl());
    }
}
