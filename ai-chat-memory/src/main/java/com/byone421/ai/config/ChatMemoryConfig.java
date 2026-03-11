package com.byone421.ai.config;

import com.byone421.ai.TokenWindowChatMemory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.MysqlChatMemoryRepositoryDialect;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.ai.chat.memory.repository.mongo.MongoChatMemoryRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class ChatMemoryConfig {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Bean
    public ChatMemory chatMemory(){
        ChatMemoryRepository chatMemoryRepository = JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new MysqlChatMemoryRepositoryDialect())
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
        return chatMemory;
    }


    /**
     * 配置mongoDB
     * @param mongoTemplate
     * @return
     */
    @Bean
    public ChatMemoryRepository mongoChatMemoryRepository(MongoTemplate mongoTemplate) {
        return MongoChatMemoryRepository.builder()
                .mongoTemplate(mongoTemplate)
                .build();
    }

    @Bean
    public ChatMemory mongoChatMemory(ChatMemoryRepository mongoChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(mongoChatMemoryRepository)
                .maxMessages(20)
                .build();
    }



    @Bean
    public VectorStore vectorStore(EmbeddingModel openAiEmbeddingModel) {
        return SimpleVectorStore.builder(openAiEmbeddingModel).build();
    }



//    @Bean
//    public ChatMemoryRepository chatMemoryRepository() {
//        return new InMemoryChatMemoryRepository();
//    }
//
//    @Bean
//    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
//        return new TokenWindowChatMemory(chatMemoryRepository, 3000);
//    }
}
