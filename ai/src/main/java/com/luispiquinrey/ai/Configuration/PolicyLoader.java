package com.luispiquinrey.ai.Configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PolicyLoader {

    @Value("classpath:politics.pdf")
    Resource policyFile;

    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void loadPdf() throws IOException {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
        List<Document> docs = tikaDocumentReader.get();
        TextSplitter textSplitter
                = TokenTextSplitter.builder()
                        .withChunkSize(300)
                        .withMaxNumChunks(1000)
                        .build();
        List<Document> splitDocs = textSplitter.split(docs)
                .stream()
                .distinct() 
                .toList();
        vectorStore.add(splitDocs);
    }
}
