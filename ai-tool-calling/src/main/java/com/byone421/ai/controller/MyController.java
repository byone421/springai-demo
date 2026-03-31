package com.byone421.ai.controller;

import com.byone421.ai.request.WeatherRequest;
import com.byone421.ai.tools.AiTools;
import com.byone421.ai.tools.Weather4Tool;
import com.byone421.ai.tools.WeatherTool;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/my")
public class MyController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;

    @Resource
    ChatModel openAiModel;
    @Resource
    private FunctionToolCallback<WeatherRequest, String> weatherTool;


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
     * 测试@Tool注解
     * @param userInput
     * @return
     */
    @GetMapping("/weather")
    public String weather(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .tools(new AiTools())
                .call()
                .content();
    }


    /**
     * 测试FunctionToolCallback
     * @param userInput
     * @return
     */
    @GetMapping("/weather2")
    public String weather2(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .tools(weatherTool)
                .call()
                .content();
    }


    /**
     * 测试自定义ToolCallback
     * @param userInput
     * @return
     */
    @GetMapping("/weather3")
    public String weather3(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .toolCallbacks(new WeatherTool())
                .call()
                .content();
    }


    /**
     * 测试ToolContext
     * @param userInput
     * @return
     */
    @GetMapping("/weather4")
    public String weather4(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .toolContext(Map.of(
                        "userId", "1001"
                ))
                .tools(new Weather4Tool())
                .call()
                .content();
    }



    @GetMapping("/order")
    public String order(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .user(userInput)
                .tools(new AiTools())
                .call()
                .content();
    }



}






