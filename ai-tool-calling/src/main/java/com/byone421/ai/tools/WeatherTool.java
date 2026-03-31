package com.byone421.ai.tools;

import com.byone421.ai.request.WeatherRequest;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import org.springframework.ai.tool.execution.ToolExecutionException;
import org.springframework.ai.util.json.JsonParser;

public class WeatherTool implements ToolCallback {

    /**
     * 定义工具（给模型看的）
     * @return
     */
    @Override
    public ToolDefinition getToolDefinition() {
        return ToolDefinition.builder()
                .name("getWeather")
                .description("根据城市查询天气，例如输入北京、上海")
                .inputSchema("""
                {
                  "type": "object",
                  "properties": {
                    "location": {
                      "type": "string",
                      "description": "城市名称，比如北京、上海、东京"
                    }
                  },
                  "required": ["location"]
                }
                """)
                .build();
    }

    /**
     * 控制工具行为
     * @return
     */
    @Override
    public ToolMetadata getToolMetadata() {
        return ToolMetadata.builder()
                .returnDirect(false) // 是否直接返回给用户
                .build();
    }

    /**
     * 执行工具（无 context）
     * @param toolInput
     * @return
     */
    @Override
    public String call(String toolInput) {
        return call(toolInput, null);
    }


    /**
     * 执行工具（有 context）
     * @param toolInput
     * @param toolContext
     * @return
     */
    @Override
    public String call(String toolInput, ToolContext toolContext) {
        try {

            WeatherRequest request = JsonParser.fromJson(toolInput, WeatherRequest.class);

            String location = request.getLocation();

            String unit = "C";
            String userId = "unknown";

            if (toolContext != null) {
                unit = (String) toolContext.getContext().getOrDefault("unit", "C");
                userId = (String) toolContext.getContext().getOrDefault("userId", "unknown");
            }


            return "用户" + userId + "查询："
                    + location + "天气是晴天，温度单位：" + unit;

        } catch (Exception e) {
            throw new ToolExecutionException(getToolDefinition(), e);
        }
    }
}