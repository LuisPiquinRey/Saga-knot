package com.luispiquinrey.ai.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ConfigurationChat {
    @Value("classpath:/prompt/contextPrompt.st")
    Resource resourceContext;

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatBuilder=ChatClient.builder(ollamaChatModel).defaultSystem(resourceContext)
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .defaultOptions(ChatOptions.builder().temperature(0.3).maxTokens(300).build());
        return chatBuilder.build();
    }
}
