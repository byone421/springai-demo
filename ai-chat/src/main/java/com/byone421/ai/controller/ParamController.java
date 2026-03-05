package com.byone421.ai.controller;

import com.byone421.ai.dto.Poem;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/param")
public class ParamController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;


    @GetMapping("/param")
    public String paramHello(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system(sp -> sp.param("name", "李白"))
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/param2")
    public String param2Hello(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system(sp -> sp.param("name", "李白"))

                .user(u -> u.text("""
                                  请写一首关于{type}的诗，要求用古诗风格，并且在诗中提到
                                """)
                        .param("type", userInput))
                .user(userInput)
                .call()
                .content();
    }


    @GetMapping("/mapParam")
    public String mapParam(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        Map<String, Object> params = Map.of(
                "name", "李白",
                "role", "架构师"
        );

        return this.openAiChatClient.prompt()
                .system(sp -> sp
                        .text("你是{name}，{role}")
                        .params(params)
                )
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/structured")
    public String structured(@RequestParam(value = "userInput", defaultValue = "") String userInput) {
        return this.openAiChatClient
                .prompt()
                .system("你是一个诗人")
                .user(u -> u.text("""
                                请根据以下参数写一首诗，要求用古诗风格。
                                诗的格式如下：
                                题目：{title}
                                作者：{author}
                                主题：{topic}
                                """).param("title", "春日随想")
                        .param("author", "李白")
                        .param("topic", "描写春天的景色与情感"))
                .call()
                .content();
    }


    /**
     * 在Spring Boot（Servlet）环境下使用流式响应
     *
     * @param userInput
     * @return
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream1(String userInput) {
        SseEmitter emitter = new SseEmitter();

        this.ollamaChatClient
                .prompt()
                .system("你是一个诗人")
                .user(userInput)
                .stream()
                .content()
                .subscribe(
                        data -> {
                            try {
                                emitter.send(SseEmitter.event().data(data));
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        emitter::completeWithError,
                        emitter::complete
                );

        return emitter;
    }


    @GetMapping(value = "/outputEntity")
    public Poem outputEntityHello(String userInput) {
        return this.ollamaChatClient
                .prompt()
                .system("你是一个诗人")
                .user(userInput)
                .call()
                .entity(Poem.class);
    }

    @GetMapping(value = "/outputMap")
    public Map<String, Object> outputMapHello() {
        return this.ollamaChatClient
                .prompt()
                .system("你是一个诗人")
                .user("""
                              生成一名诗人的信息，要求输出一个包含以下字段的JSON对象:
                              - name
                              - age
                              - dynasty
                              JSON的格式如下:
                              {
                                  "name": "李白",
                                  "age": 42,
                                  "dynasty": "唐代"
                              }
                        """)
                .call()
                .entity(new ParameterizedTypeReference<Map<String, Object>>() {
                });
    }

    @GetMapping(value = "/outputList")
    public List<Poem> outputListHello() {
        var converter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<Poem>>() {
                }
        );

        return ollamaChatClient.prompt()
                .system("你是一个诗人")
                .user("""
                        推荐3个诗人的作品，返回作品名和作者和作品内容:
                        请返回一个 JSON 数组。
                        格式示例：
                        [
                          {
                            "title": "诗名",
                            "author": "作者"
                            "content": "诗的内容"
                          }
                        ]
                        不要返回对象，不要嵌套。
                        """)
                .call()
                .entity(converter);
    }

    @GetMapping(value = "/outputListNative")
    public List<Poem> outputListNativeHello() {
        var converter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<Poem>>() {
                }
        );

        return ollamaChatClient.prompt()
                .system("你是一个诗人")
                .user("""
                        推荐3个诗人的作品，返回作品名和作者和作品内容:
                        """)
                .call()
                .entity(converter);
    }



}






