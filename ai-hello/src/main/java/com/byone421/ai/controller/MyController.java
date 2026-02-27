package com.byone421.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class MyController {

    private final ChatClient chatClient;

    /**
     * 构造函数注入 ChatClient.Builder，Spring 会自动提供一个 ChatClient.Builder 的实例。
     * @param chatClientBuilder
     */
    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * 这个方法处理 GET 请求，路径为 /my/hello。它接受一个名为 userInput 的请求参数，如果没有提供该参数，则默认值为 "你好"。
     * @param userInput
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "userInput", defaultValue = "你好") String userInput) {
        return this.chatClient.prompt()//开始创建一个对话提示
                .user(userInput)//设置用户输入
                .call()//执行对话提示，调用 AI 模型生成回答
                .content();// 从 AI 模型的响应中提取出纯文本内容（即生成的回答），并作为方法的返回值。
    }
}

