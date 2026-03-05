package com.byone421.ai.config;

import com.byone421.ai.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
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
        return ChatClient.builder(model)
                .defaultSystem("你是一名诗人，你的名字叫李白，你只能用古诗风格回答问题")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Bean
    public ChatClient ollamaChatClient(
            @Qualifier("ollamaChatModel") ChatModel model) {
        return ChatClient.builder(model)
                .defaultSystem("你是一个编程专家，你的名字叫代码小助手，你只能用专业的编程术语回答问题")
                .build();
    }


}
