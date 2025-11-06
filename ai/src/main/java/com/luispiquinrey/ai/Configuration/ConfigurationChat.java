package com.luispiquinrey.ai.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationChat {
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatBuilder=ChatClient.builder(ollamaChatModel);
        return chatBuilder.build();
    }
}
