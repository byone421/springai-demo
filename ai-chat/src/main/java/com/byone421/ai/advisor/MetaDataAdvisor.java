package com.byone421.ai.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

import java.util.Map;


public class MetaDataAdvisor implements CallAdvisor {

    @Override
    public String getName() {
        return "MetaDataAdvisor";
    }

    /**
     * 控制Advisor的执行顺序，数值越小优先级越高，默认值为0
     *
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

        System.out.println("===== UserAdvisor CALL BEFORE =====");
        Map<String, Object> userMap = request.prompt().getUserMessage().getMetadata();
        userMap.forEach((s, o) -> System.out.println("UserAdvisor 拦截的user-metadata: " + s + " - " + o));
        Map<String, Object> systemMap = request.prompt().getSystemMessage().getMetadata();
        systemMap.forEach((s, o) -> System.out.println("UserAdvisor 拦截的system-metadata: " + s + " - " + o));

        // 调用下一个 Advisor
        ChatClientResponse response = chain.nextCall(request);
        System.out.println("=====UserAdvisor CALL AFTER =====");
        System.out.println("UserAdvisor Response: " + response.chatResponse().getResult().getOutput().getText());
        return response;
    }

}
