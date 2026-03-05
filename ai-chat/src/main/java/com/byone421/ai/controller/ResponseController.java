package com.byone421.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;

    @Resource
    ChatModel openAiModel;



    @GetMapping("/hello")
    public void  hello() {
        ChatResponse response = openAiChatClient.prompt()
                .user("用写诗的方式讲个笑话")
                .call()
                .chatResponse();
        for (Generation result : response.getResults()) {
            System.out.println("---------result.getOutput-----------");
            System.out.println("result.getOutput().getText():" + result.getOutput().getText());
            System.out.println("result.getOutput().getMetadata():" + result.getOutput().getMetadata());
            System.out.println("---------result.getOutput-----------");
        }

    }


    @GetMapping("/token")
    public void  token() {
        ChatResponse response = openAiChatClient.prompt()
                .user("用写诗的方式讲个笑话")
                .call()
                .chatResponse();
        ChatResponseMetadata metadata = response.getMetadata();
        String model = metadata.getModel();
        System.out.println("model:" + model);
        Usage usage = metadata.getUsage();
        System.out.println("Prompt Tokens: " + usage.getPromptTokens());
        System.out.println("Completion Tokens: " + usage.getCompletionTokens());
        System.out.println("Total Tokens: " + usage.getTotalTokens());
    }


}






