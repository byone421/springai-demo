package com.byone421.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    ChatClient openAiChatClient;

    @GetMapping("/chat")
    public Flux<String> chat(String msg) {

        return openAiChatClient.prompt()
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping("/mcp/chat")
    public Flux<String> chatMcp(String msg) {

        return openAiChatClient.prompt()
                .user(msg)
                .stream()
                .content();
    }
}