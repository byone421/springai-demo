package com.byone421.ai;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.join.DocumentJoiner;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiQueryDocumentRetriever implements DocumentRetriever {

    private final MultiQueryExpander expander;
    private final DocumentRetriever delegate;
    private final DocumentJoiner joiner;

    public MultiQueryDocumentRetriever(MultiQueryExpander expander,
                                       DocumentRetriever delegate) {
        this.expander = expander;
        this.delegate = delegate;
        this.joiner = new ConcatenationDocumentJoiner();
    }

    @Override
    public List<Document> retrieve(Query query) {

        List<Query> queries = expander.expand(query);

        Map<Query, List<List<Document>>> documentsForQuery = new HashMap<>();

        for (Query q : queries) {
            List<List<Document>> docsFromSources = new ArrayList<>();

            // 这里只用一个数据源（vector）
            docsFromSources.add(delegate.retrieve(q));

            documentsForQuery.put(q, docsFromSources);
        }

        return joiner.join(documentsForQuery);
    }
}