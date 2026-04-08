package com.byone421.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {



    @Bean("openAiModel")
    public ChatModel openAiModel(OpenAiChatModel openAiChatModel) {
        return openAiChatModel;
    }


    @Bean
    public ChatClient openAiChatClient(@Qualifier("openAiModel") ChatModel chatModel, ToolCallbackProvider tools) {
        return ChatClient.builder(chatModel)
                //通过了mcp协议获取了远程工具，在配置文件中有相关的配置
                .defaultToolCallbacks(tools.getToolCallbacks())
                .build();
    }


}