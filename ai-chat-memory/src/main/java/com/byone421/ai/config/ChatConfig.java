package com.byone421.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
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
    public ChatClient openAiChatClient(@Qualifier("openAiModel") ChatModel model,@Qualifier("mongoChatMemory") ChatMemory mongoChatMemory) {
        return ChatClient.builder(model)
                 //添加MessageChatMemoryAdvisor
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(mongoChatMemory).build())
                .build();
    }

    @Bean
    public ChatClient ollamaChatClient(
            @Qualifier("ollamaChatModel") ChatModel model) {
        return ChatClient.builder(model)
                .build();
    }


}
