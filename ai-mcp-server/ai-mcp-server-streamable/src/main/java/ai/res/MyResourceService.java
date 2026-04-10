package ai.res;


import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MyResourceService {



    @McpResource(uri = "resource://docs/help")
    public Mono<String> getHelpDoc() {
        return Mono.just("""
                # 系统帮助文档
                - 使用 `getWeather` 工具查询天气
                - 使用 `calculator` 工具进行计算
                """);
    }
}
