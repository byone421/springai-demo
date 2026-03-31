package com.byone421.ai.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;

public class Weather4Tool {


    @Tool(description = "根据城市查询天气")
    public String getWeather4(String location, ToolContext context) {
        //从ToolContext 获取隐藏参数
        String userId = (String) context.getContext().get("userId");
        System.out.println("用户查询了天气:" + userId);
        return location + "天气是晴天";
    }
}
