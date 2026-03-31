package com.byone421.ai.config;

import com.byone421.ai.request.WeatherRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean("openAiModel")
    public ChatModel openAiModel(OpenAiChatModel openAiChatModel) {
        return openAiChatModel;
    }


    @Bean("ollamaChatModel")
    public ChatModel ollamaChatModel(OllamaChatModel ollamaChatModel) {
        return ollamaChatModel;
    }

    @Bean
    public ChatClient openAiChatClient(
            @Qualifier("openAiModel") ChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean
    public ChatClient ollamaChatClient(
            @Qualifier("ollamaChatModel") ChatModel model) {
        return ChatClient.builder(model).build();
    }


    @Bean
    public FunctionToolCallback<WeatherRequest, String> weatherTool() {
        return FunctionToolCallback.<WeatherRequest, String>builder(
                        "getWeather2",
                        request -> request.getLocation() + "的天气是晴天"
                )
                .description("根据城市查询天气信息")
                .inputType(WeatherRequest.class)
                .build();
    }



}
