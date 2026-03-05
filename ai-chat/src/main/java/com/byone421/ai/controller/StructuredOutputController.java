package com.byone421.ai.controller;

import com.byone421.ai.dto.Poem;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/structured")
public class StructuredOutputController {

    @Resource
    ChatClient openAiChatClient;

    @Resource
    ChatClient ollamaChatClient;


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






