package com.infinite.newsAI.config;

import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OllamaConfig {

    @Bean
    public OllamaApi ollamaApi() {
        return new OllamaApi("http://localhost:11434");
    }

    @Bean
    public OllamaChatModel chatModel(OllamaApi ollamaApi) {
        return new OllamaChatModel(
                ollamaApi,
                OllamaOptions.builder().model("llama3:latest").build(),
                DefaultToolCallingManager.builder().build(),
                ObservationRegistry.NOOP,
                ModelManagementOptions.defaults()
        );
    }
}
