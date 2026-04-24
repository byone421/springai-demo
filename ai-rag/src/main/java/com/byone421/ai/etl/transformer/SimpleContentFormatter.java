package com.byone421.ai.etl.transformer;

import org.springframework.ai.document.ContentFormatter;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;

public class SimpleContentFormatter implements ContentFormatter {

    @Override
    public String format(Document document, MetadataMode mode) {
        String content = document.getText();

        if (content == null) {
            return "";
        }

        // 1. 去多余空白
        content = content.replaceAll("\\s+", " ");

        // 2. 去多余换行
        content = content.replaceAll("\\n+", "\n");

        // 3. trim
        return content.trim();
    }
}