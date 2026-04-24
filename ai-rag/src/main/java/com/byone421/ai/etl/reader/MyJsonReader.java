package com.byone421.ai.etl.reader;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyJsonReader {

	private final Resource resource;

    public MyJsonReader(@Value("classpath:json-source.json") Resource resource) {
        this.resource = resource;
    }

	public List<Document> loadJsonAsDocuments() {
        JsonReader jsonReader = new JsonReader(this.resource);
        return jsonReader.get();
	}
}