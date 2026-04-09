package ai.res;


import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class DemoResourceService {


    /**
     * Resource 示例： 帮我看看帮助文档 resource://docs/help
     * URI：resource://docs/help
     */
    @McpResource(uri = "resource://docs/help")
    public Mono<String> getHelpDoc() {
        return Mono.just("""
                # 系统帮助文档
                - 使用 `getWeather` 工具查询天气
                - 使用 `calculator` 工具进行计算
                """);
    }
}
