package com.luispiquinrey.ai.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationChat {

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatBuilder=ChatClient.builder(ollamaChatModel).defaultSystem("You are the clerk of the Knot Store. Respond to the user concisely and professionaly. " + 
            "Do not use emojis or decoratives symbols. Each response must be no longer than 150 characters, do not add extra explanations unless the user explicility asks. " + 
            "Always maintain a polite, direct, and helpful tone. Only respond to questions or comments directly related to your role as the store clerk.");
        return chatBuilder.build();
    }
}
