package ai.prompt;

import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class DemoPromptService {

    /**
     * 让AI生成工作日报，如果AI没有主动调用。可以明确使用daily_report工具。
     * 比如：使用 daily_report 生成日报，content=今天写了接口开发，role=Java工程师
     */
    @McpPrompt(name = "daily_report", description = "生成工作日报")
    public Mono<Prompt> dailyReportPrompt(
            @McpArg(name = "content", description = "今日工作内容") String content,
            @McpArg(name = "role", description = "你的角色") String role) {

        // 方法2：手动构建 Prompt（更灵活）
        SystemMessage systemMsg = new SystemMessage(String.format("""
            你是一个%s，请根据用户的工作内容生成一份专业的工作日报。
            日报需要包含：工作摘要、完成情况、遇到的问题、明日计划。
            使用 markdown 格式，语气专业正式。
            """, role));

        UserMessage userMsg = new UserMessage("今日工作内容：" + content);
        return Mono.just(new Prompt(systemMsg, userMsg));
    }
}