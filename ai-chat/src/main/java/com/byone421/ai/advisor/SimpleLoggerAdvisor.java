package com.byone421.ai.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

/**
 *  CallAdvisor: 实现CallAdvisor拦截同步call调用
 *  StreamAdvisor: 实现StreamAdvisor拦截流式调用
 *  根据需求实现对应的接口，两个接口都实现则两个场景都能拦截
 */
public class SimpleLoggerAdvisor implements CallAdvisor,StreamAdvisor {

    @Override
    public String getName() {
        return "SimpleLoggerAdvisor";
    }

    /**
     * 控制Advisor的执行顺序，数值越小优先级越高，默认值为0
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * @param request
     * @param chain
     * @return
     */
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {

        System.out.println("===== SimpleLoggerAdvisor CALL BEFORE =====");
        System.out.println("SimpleLoggerAdvisor User Prompt: " + request.prompt());

        long start = System.currentTimeMillis();

        // 调用下一个 Advisor
        ChatClientResponse response = chain.nextCall(request);

        long cost = System.currentTimeMillis() - start;

        System.out.println("=====SimpleLoggerAdvisor CALL AFTER =====");
        System.out.println("SimpleLoggerAdvisor Response: " + response.chatResponse().getResult().getOutput().getText());
        System.out.println("耗时: " + cost + " ms");


        return response;
    }

    /**
     * @param request
     * @param chain
     * @return
     */
    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
        System.out.println("===== STREAM BEFORE =====");
        System.out.println("User Prompt: " + request.prompt());

        long start = System.currentTimeMillis();

        return chain.nextStream(request)
                .doOnNext(chunk -> {
                    System.out.println("流式片段: " +
                            chunk.chatResponse().getResult().getOutput().getText());
                })
                .doOnComplete(() -> {
                    long cost = System.currentTimeMillis() - start;
                    System.out.println("===== STREAM COMPLETE =====");
                    System.out.println("耗时: " + cost + " ms");
                });
    }
}
