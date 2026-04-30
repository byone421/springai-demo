package com.byone421.ai;

import com.alibaba.cloud.ai.dashscope.audio.transcription.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.transcription.DashScopeAudioTranscriptionOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeModel;
import com.networknt.schema.resource.ResourceLoader;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@SpringBootTest
public class TranscriptionTest {

    @Autowired
    private DashScopeAudioTranscriptionModel dashScopeTranscriptionModel;

    @Test
    public void test() {
        // 1. 构建语音识别参数
        DashScopeAudioTranscriptionOptions options =
                DashScopeAudioTranscriptionOptions.builder()
                        .model(DashScopeModel.AudioModel.PARAFORMER_V1.getValue())
                        .languageHints(List.of("zh", "en")) // 支持中英文混合识别
                        .disfluencyRemovalEnabled(false)   // 是否去除口语停顿（如“嗯”、“啊”）
                        .punctuationPredictionEnabled(true) // 自动补全标点
                        .build();

        // 2. 加载本地音频文件
        ClassPathResource audioFile = new ClassPathResource("xxx.mp3");

        // 3. 构建请求
        AudioTranscriptionPrompt request =
                new AudioTranscriptionPrompt(audioFile, options);

        // 4. 调用模型
        AudioTranscriptionResponse response =
                dashScopeTranscriptionModel.call(request);

        // 5. 输出识别结果
        System.out.println(response.getResult().getOutput());
    }
}