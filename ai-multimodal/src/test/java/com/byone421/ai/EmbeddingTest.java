package com.byone421.ai;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EmbeddingTest {

    @Autowired
    private DashScopeEmbeddingModel embeddingModel;

    @Test
    public void test() {
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of("我喜欢编程"),
                DashScopeEmbeddingOptions.builder().model("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));
    }
}
