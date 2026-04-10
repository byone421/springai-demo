package ai.prompt;

import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpComplete;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MyPromptService {

    /**
     * 让AI生成工作日报，如果AI没有主动调用。可以明确使用daily_report工具。
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