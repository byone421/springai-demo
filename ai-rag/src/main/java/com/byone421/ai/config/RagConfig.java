package com.byone421.ai.config;


import com.byone421.ai.MultiQueryDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.join.DocumentJoiner;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class RagConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel openAiEmbeddingModel) {
        return SimpleVectorStore.builder(openAiEmbeddingModel).build();
    }


    @Bean
    public Advisor myRetrievalAugmentationAdvisor(
            VectorStore pineconeVectorStore,
            ChatClient.Builder chatClientBuilder
    ) {

        return RetrievalAugmentationAdvisor.builder()
                //QueryTransformer（查询增强 / 重写）
                //把用户问题“翻译成更适合检索的 query”
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClientBuilder.build().mutate())
                                .build()
                )
                //DocumentRetriever（检索层）
                .documentRetriever(

                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(pineconeVectorStore)
                                .similarityThreshold(0.5)
                                .topK(3)
                                .build()
                )
                //DocumentPostProcessor（文档后置处理）
                .documentPostProcessors(new DocumentPostProcessor[]{
                        new MyDocumentPostProcessor()
                })
                //ContextualQueryAugmenter（上下文控制）
                .queryAugmenter(
                        ContextualQueryAugmenter.builder()
                                .allowEmptyContext(true) // 如果检索结果为空，是否允许 LLM 自主回答
                                .build()
                )
                .build();
    }

}
