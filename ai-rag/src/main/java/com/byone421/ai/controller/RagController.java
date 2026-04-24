package com.byone421.ai.controller;


import com.byone421.ai.etl.reader.MyMarkdownReader;
import com.byone421.ai.etl.transformer.SimpleContentFormatter;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {

    @Resource
    VectorStore vectorStore;

    @Resource
    VectorStore pineconeVectorStore;

    @Resource
    MyMarkdownReader myMarkdownReader;

    @Resource
    ChatClient openAiChatClient;
    @Resource(name = "myRetrievalAugmentationAdvisor")
    Advisor myRetrievalAugmentationAdvisor;

    @GetMapping("/qa")
    public String testRag(String userText) {

        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .similarityThreshold(0.3d) // 相似度阈值 (根据实际情况调整，过高可能没有结果，过低可能引入噪声)
                        .topK(6)                   // 返回数量topK 不要太大（避免噪声）
                        .build())
                .build();
        return openAiChatClient.prompt()
                .advisors(qaAdvisor)
                .user(userText)
                .call()
                .content();
    }


    @GetMapping("/qa2")
    public String testRag2(String userText) {

        PromptTemplate customPromptTemplate = PromptTemplate.builder()
                .template("""
          你是一个实用的AI助手.

          用户问题:
          {query}

          上下文信息如下:
          ---------------------
          {question_answer_context}
          ---------------------

          Rules:
          1. 如果上下文没有答案，回答：不知道
          2. 不要说“根据上下文”
          3. 不要编造
          """)
                .build();

        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .searchRequest(SearchRequest.builder()
                        .similarityThreshold(0.5d)
                        .topK(5)
                        .build())
                .build();

        return openAiChatClient.prompt()
                .advisors(qaAdvisor)
                .user(userText)
                .call()
                .content();
    }

    @GetMapping("/qa3")
    public String testRag3(String userText) {
        return openAiChatClient.prompt()
                .advisors(myRetrievalAugmentationAdvisor)
                .user(userText)
                .call()
                .content();
    }




    @GetMapping("/ingestMarkdownToPinecone")
    public void ingestMarkdownToPinecone() {
        // 1. 读取
        List<Document> documents = myMarkdownReader.loadMarkdown();

        // 2. 切分
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(300)
                .withMinChunkLengthToEmbed(20)
                .build();

        List<Document> chunks = splitter.apply(documents);

        // 3. 清洗
        ContentFormatTransformer formatter = new ContentFormatTransformer(new SimpleContentFormatter());
        chunks = formatter.apply(chunks);

        // 4. 入库
        pineconeVectorStore.add(chunks);
    }

    @GetMapping("/qa4")
    public String testRag4(String userText) {
        return openAiChatClient.prompt()
                .advisors(myRetrievalAugmentationAdvisor)
                .user(userText)
                .call()
                .content();
    }





}
