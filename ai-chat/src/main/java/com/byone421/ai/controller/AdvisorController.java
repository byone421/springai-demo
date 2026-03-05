package com.byone421.ai.controller;

import com.byone421.ai.advisor.SimpleLoggerAdvisor;
import com.byone421.ai.advisor.MetaDataAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class AdvisorController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;

    @Resource
    ChatModel openAiModel;


    @GetMapping("/simple")
    public String simple(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system(sp -> sp.param("name", "李白"))
                .user(userInput)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }


    @GetMapping("/multiple")
    public String multiple(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system(sp -> sp.param("name", "李白"))
                .user(userInput)
                .advisors(new SimpleLoggerAdvisor())
                .advisors(new MetaDataAdvisor())
                .call()
                .content();
    }

    @GetMapping("/metadata")
    public String metadata(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system(sp -> {
                            sp.metadata("version", "1.0");
                            sp.param("name", "李白");
                        }
                )
                .user((up) -> {
                    up.text(userInput);
                    up.metadata("userID", "123");
                })
                .advisors(new MetaDataAdvisor())
                .call()
                .content();
    }
}






