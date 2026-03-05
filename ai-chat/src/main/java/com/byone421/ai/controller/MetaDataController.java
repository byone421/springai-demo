package com.byone421.ai.controller;

import com.byone421.ai.dto.Poem;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metadata")
public class MetaDataController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;


    @GetMapping(value = "/metadata")
    public String metadata() {
        return ollamaChatClient.prompt().user(u -> u.text("你好")
                        .metadata("messageId", "xxxxx")
                        .metadata("userId", "1"))
                .call()
                .content();
    }

    @GetMapping(value = "/map")
    public String metadataMap() {
        Map<String, Object> userMetadata = Map.of(
                "userId", "xxxx",
                "timestamp", System.currentTimeMillis()
        );

        return ollamaChatClient.prompt()
                .user(u -> u.text("你好")
                        .metadata(userMetadata))
                .call()
                .content();
    }
}






