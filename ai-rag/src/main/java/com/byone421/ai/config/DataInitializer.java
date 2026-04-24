package com.byone421.ai.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final VectorStore vectorStore;

    public DataInitializer(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void init() {
        List<Document> documents = List.of(
                new Document("Spring AI 是一个用于构建 AI 应用的框架，支持对接大模型、向量数据库和RAG流程。"),
                new Document("RAG（检索增强生成）通过从向量数据库中检索相关文档来增强大模型回答的准确性。"),
                new Document("VectorStore 是用于存储文本向量并支持相似度搜索的组件，比如 Redis、Milvus、PGVector。"),
                new Document("SimpleVectorStore 是一个基于内存的向量存储实现，适用于测试和开发环境。"),
                new Document("Embedding 是将文本转换为向量的过程，用于语义搜索和相似度计算。"),
                new Document("topK 表示从向量数据库中返回最相似的前K条数据。"),
                new Document("similarityThreshold 用于控制相似度过滤，值越高结果越严格。"),
                new Document("Spring Boot 可以很方便地集成 Spring AI 来构建智能问答系统。")
        );



        vectorStore.add(documents);
    }
}