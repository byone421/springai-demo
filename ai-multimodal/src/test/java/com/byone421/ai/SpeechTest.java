package com.byone421.ai;



import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechModel;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class SpeechTest {

    @Autowired
    private DashScopeAudioSpeechModel dashScopeAudioSpeechModel;

    @Test
    public void test() throws Exception {
        // 1. 配置语音参数
        DashScopeAudioSpeechOptions speechOptions =
                DashScopeAudioSpeechOptions.builder()
                        .model(DashScopeModel.AudioModel.COSYVOICE_V1.getValue())
                        .voice("longhua") // 发音人
                        .build();

        // 2. 构建文本转语音请求
        TextToSpeechPrompt speechPrompt =
                new TextToSpeechPrompt("支付宝到账1万元", speechOptions);

        // 3. 调用模型生成语音
        TextToSpeechResponse response =
                dashScopeAudioSpeechModel.call(speechPrompt);

        byte[] audioData = response.getResult().getOutput();

        // 4. 保存为音频文件
        try (FileOutputStream fos = new FileOutputStream("xxx.mp3")) {
            fos.write(audioData);
            System.out.println("文件保存成功: xxx.mp3");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件保存失败: " + e.getMessage());
        }
    }
}