package com.byone421.ai.controller;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class ChatMemoryAdvisorController {

    @Resource
    ChatClient openAiChatClient;
    @Resource
    ChatMemory mongoChatMemory;
    @Resource
    VectorStore vectorStore;

    @GetMapping("/message")
    public String message(String userMessage) {
        //指定是哪个对话id
        String conversationId = "009" ;
        // 1. 保存用户消息
        String response = openAiChatClient.prompt()
                .user(userMessage)
                //给advisors设置参数
                .advisors(a ->{
                    a.param(ChatMemory.CONVERSATION_ID, conversationId);
                })
                .call()
                .content();


        return response;
    }


    @GetMapping("/prompt")
    public String promptChatMemory(String userMessage) {

        PromptTemplate template = PromptTemplate.builder()
                .template("""
                    系统指令:
                    {instructions}
                    
                    历史对话:
                    {memory}
                    
                    """)
                .build();

        String chatClient = openAiChatClient.prompt()
                .advisors(
                        PromptChatMemoryAdvisor.builder(mongoChatMemory)
                                .systemPromptTemplate(template)
                                .conversationId("009")
                                .build()
                )
                .user(userMessage)
                .call()
                .content();
        return chatClient;
    }


    @GetMapping("/vector")
    public String vectorStoreChatMemory(String userMessage) {
        PromptTemplate template = PromptTemplate.builder()
                .template("""
                    系统指令:
                    {instructions}
                    
                    历史对话:
                    {long_term_memory}
                   
                    """)
                .build();

        String chatClient = openAiChatClient.prompt()
                .advisors(
                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                //取出前20条
                                .defaultTopK(20)
                                .conversationId("123")
                                .systemPromptTemplate(template)
                                .build()
                )
                .user(userMessage)
                .call()
                .content();
        return chatClient;
    }





}
