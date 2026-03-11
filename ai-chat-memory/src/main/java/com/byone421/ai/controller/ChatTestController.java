package com.byone421.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatTestController {

    @Resource
    private ChatMemory chatMemory;
    @Resource
    ChatClient openAiChatClient;
    @Resource
    private ChatMemory mongoChatMemory;


    @GetMapping("/memory")
    public String chatExample(String userMessage) {
        String conversationId = "test-id";

        // 1. 保存用户消息

        chatMemory.add(conversationId, List.of(
                new UserMessage(userMessage)
        ));


        // 2. 获取当前上下文（保留最近消息）
        List<Message> context = chatMemory.get(conversationId);

        // 3. 调用 ChatClient，让模型回答
        String content = openAiChatClient.prompt()
                .messages(context)
                .call().content();

        // 4. 保存模型回答
        chatMemory.add(conversationId, List.of(new AssistantMessage(content)));

        // 5. 输出
        System.out.println("AI: " + content);
        return content;
    }

    /**
     * 测试mongodb
     * @param userMessage
     * @return
     */
    @GetMapping("/memory/mongo")
    public String memoryMongo( String userMessage) {
        String conversationId = "test-id-mongo";
        // 1. 保存用户消息
        mongoChatMemory.add(conversationId, List.of(
                new UserMessage(userMessage)
        ));

        // 2. 获取当前上下文（保留最近消息）
        List<Message> context = mongoChatMemory.get(conversationId);

        // 3. 调用 ChatClient，让模型回答
        String content = openAiChatClient.prompt()
                .messages(context)
                .call().content();
        // 4. 保存模型回答
        mongoChatMemory.add(conversationId, List.of(new AssistantMessage(content)));
        // 5. 输出
        System.out.println("AI: " + content);
        return content;
    }

}