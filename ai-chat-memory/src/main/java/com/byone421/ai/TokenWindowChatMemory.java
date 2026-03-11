package com.byone421.ai;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个简化版 TokenWindowChatMemory 示例，其主要逻辑是：
 * 1. 先从 ChatMemoryRepository 取出历史消息
 * 2. 计算这些消息的 Token 数量
 * 3. 如果超过最大 Token 限制，就 从最早的消息开始删除
 * 4. 保留 Token 数量在限制以内的消息
 */
public class TokenWindowChatMemory implements ChatMemory {

    private final ChatMemoryRepository repository;

    /**
     * 最大 token 数
     */
    private final int maxTokens;

    public TokenWindowChatMemory(ChatMemoryRepository repository, int maxTokens) {
        this.repository = repository;
        this.maxTokens = maxTokens;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        repository.saveAll(conversationId, messages);

        List<Message> allMessages = repository.findByConversationId(conversationId);

        List<Message> trimmed = trimToTokenLimit(allMessages);

        repository.deleteByConversationId(conversationId);
        repository.saveAll(conversationId, trimmed);
    }

    @Override
    public List<Message> get(String conversationId) {
        return repository.findByConversationId(conversationId);
    }

    @Override
    public void clear(String conversationId) {
        repository.deleteByConversationId(conversationId);
    }

    /**
     * 根据 token 数量裁剪消息
     */
    private List<Message> trimToTokenLimit(List<Message> messages) {

        List<Message> result = new ArrayList<>();
        int totalTokens = 0;

        // 从最后一条消息开始往前保留
        for (int i = messages.size() - 1; i >= 0; i--) {

            Message message = messages.get(i);

            int tokens = estimateTokens(message);

            if (totalTokens + tokens > maxTokens) {
                break;
            }
            result.add(0, message);
            totalTokens += tokens;
        }

        return result;
    }

    /**
     * 简单token估算
     */
    private int estimateTokens(Message message) {
        if (message.getText() == null) {
            return 0;
        }
        // 简单估算：4字符 大约 1 token
        return message.getText().length() / 4;
    }
}