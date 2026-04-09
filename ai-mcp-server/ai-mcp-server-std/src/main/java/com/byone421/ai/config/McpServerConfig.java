package com.byone421.ai.config;

import com.byone421.ai.tool.WeatherTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig
{
    @Bean
    public ToolCallbackProvider weatherTools(WeatherTool weatherTool)
    {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherTool)
                .build();
    }
}
