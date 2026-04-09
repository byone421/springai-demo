package com.byone421.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool(description = "根据城市查询天气")
    public String getWeather(String city) {
        return city + " 的天气是：多云 20°C";
    }
}
