package com.luispiquinrey.ai.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ConfigurationChat {

    @Value("classpath:/prompt/contextPrompt.st")
    Resource resourceContext;

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .topK(10)
                                .similarityThreshold(0.0)
                                .build())
                .build();
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel,
            RetrievalAugmentationAdvisor retrievalAugmentationAdvisor,
            ChatMemory chatMemory) throws IOException {

        String systemPrompt = Files.readString(resourceContext.getFile().toPath());

        return ChatClient.builder(ollamaChatModel)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(List.of(
                        new SimpleLoggerAdvisor(),
                        retrievalAugmentationAdvisor,
                        MessageChatMemoryAdvisor.builder(chatMemory).build()))
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.1)
                        .maxTokens(5000)
                        .build())
                .build();
    }
}
