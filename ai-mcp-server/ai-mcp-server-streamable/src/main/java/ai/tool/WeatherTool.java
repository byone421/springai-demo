package ai.tool;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class WeatherTool {

    @Tool(description = "根据城市查询天气")
    public String getWeather(String city) {
        return city + " 的天气是：多云 20°C";
    }


    @McpTool(name = "calculator", description = "简单的数学计算器")
    public Mono<Double> calculate(
            @McpToolParam(description = "运算符: add, sub, mul, div") String op,
            @McpToolParam(description = "第一个操作数") double a,
            @McpToolParam(description = "第二个操作数") double b) {

        double result = switch (op) {
            case "add" -> a + b;
            case "sub" -> a - b;
            case "mul" -> a * b;
            case "div" -> a / b;
            default -> throw new IllegalArgumentException("Unsupported operator");
        };

        return Mono.just(result);
    }
}
