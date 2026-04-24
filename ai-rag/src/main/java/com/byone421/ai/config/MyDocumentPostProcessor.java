package com.byone421.ai.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;

import java.util.List;

public class MyDocumentPostProcessor implements DocumentPostProcessor {
    @Override
    public List<Document> process(Query query, List<Document> documents) {

        System.out.println("MyDocumentPostProcessor#process called执行了: ");
        System.out.println("query: " + query.text());
        System.out.println("documents: " + documents.toString());
        return documents.stream()
                .distinct()
                .map(doc -> {
                    String content = doc.getText();

                    // 防止上下文过长
                    if (content.length() > 200) {
                        content = content.substring(0, 200);
                    }

                    return new Document(content, doc.getMetadata());
                })
                .toList();
    }
}
