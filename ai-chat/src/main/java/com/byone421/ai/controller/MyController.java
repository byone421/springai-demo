package com.byone421.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my")
public class MyController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;

    @Resource
    ChatModel openAiModel;


    /**
     * @param userInput
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .call()
                .content();
    }

    /**
     * 手动ChatModel调用示例，展示了如何创建 Prompt 对象并直接调用 ChatModel 的 call 方法来获取响应。
     * @param userInput
     * @return
     */
    @GetMapping("/chatModel")
    public String chatModelHello(@RequestParam(value = "userInput", defaultValue = "") String userInput) {

        //手动创建一个 Prompt 对象
        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage("你是唐代诗人李白，只能用古诗风格回答问题。"),
                        new UserMessage(userInput)
                )
        );
        //同步调用 ChatModel 的 call 方法，传入创建的 Prompt 对象，获取 ChatResponse 对象。
        ChatResponse response = openAiModel.call(prompt);

        return response.getResult()
                .getOutput()
                .getText();
    }


    @GetMapping("/multipleChatModel")
    public String multipleChatModelHello(@RequestParam(value = "userInput", defaultValue = "") String userInput,
                                         @RequestParam(value = "model", defaultValue = "") String model) {
        if ("openAi".equals(model)) {
            return this.openAiChatClient
                    .prompt()
                    .user(userInput)
                    .call()
                    .content();

        } else {
            return this.ollamaChatClient
                    .prompt()
                    .user(userInput)
                    .call()
                    .content();
        }
    }

}






