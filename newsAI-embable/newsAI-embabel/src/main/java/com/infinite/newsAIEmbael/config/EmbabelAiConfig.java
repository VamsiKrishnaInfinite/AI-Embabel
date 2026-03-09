package com.infinite.newsAIEmbael.config;

import com.embabel.agent.spi.support.springai.SpringAiLlmService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class EmbabelAiConfig {

    @Bean(name = {"gpt-4.1-mini", "llama3:latest"})
    public SpringAiLlmService llama3LlmService(ChatModel chatModel) {
        // SpringAiLlmService is the concrete class used in 0.3.4
        // to wrap any Spring AI ChatModel (like Ollama).
        return new SpringAiLlmService(
                "gpt-4.1-mini", // Alias to satisfy internal validator
                "ollama",       // Provider name
                chatModel
        );
    }
    @Bean
    public OllamaApi ollamaApi() {
        // Explicitly using the URL from your properties
        return new OllamaApi("http://localhost:11434");
    }


    @Bean
    public OllamaChatModel chatModel(OllamaApi ollamaApi) {
        // 1. Basic Model Options
        OllamaOptions options = OllamaOptions.builder()
                .model("llama3:latest")
                .build();

        // 2. Fix the "List" error: Use the internal NO_OP instance for Tools
        // In Spring AI M6+, this is usually the simplest way to satisfy the requirement
        ToolCallingManager toolCallingManager = ToolCallingManager.builder().build();

        // 3. Fix the ObservationRegistry error: Use the static NOOP instance
        ObservationRegistry observationRegistry = ObservationRegistry.NOOP;

        // 4. Default Management (don't pull model on every start)
        ModelManagementOptions modelManagementOptions = ModelManagementOptions.defaults();

        // 5. Invoke the constructor with the exact types required
        return new OllamaChatModel(
                ollamaApi,
                options,
                toolCallingManager,
                observationRegistry,
                modelManagementOptions
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public RestClient.Builder restClientBuilder() {
        // We return a raw builder to satisfy the dependency
        // injection requirement of the Ollama starter.
        return RestClient.builder();
    }
    @Bean
    @SuppressWarnings("deprecation") // Required because Embabel 0.3.4 still looks for the old type
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        // We instantiate it manually to satisfy the 'required bean'
        // while acknowledging Spring 7.0's move toward JsonMapper.
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.configure(JsonMapper.builder().build());
        return builder;
    }
}
