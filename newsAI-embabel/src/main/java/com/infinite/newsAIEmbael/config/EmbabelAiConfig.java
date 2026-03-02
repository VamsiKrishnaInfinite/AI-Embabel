package com.infinite.newsAIEmbael.config;

import com.embabel.common.ai.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration helpers to register an Llm bean and a ModelProvider that maps aliases to the provider.
 */
@Configuration
public class EmbabelAiConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmbabelAiConfig.class);

    /**
     * This bean 'tricks' the Embabel internal validator.
     * It names the Ollama model 'gpt-4.1-mini' so the
     * AgentPlatformConfiguration doesn't throw the exception.
     */
    @Bean(name = {"gpt-4.1-mini", "llama3:latest", "llama3"})
    public Llm llama3Llm(ChatModel chatModel) {
        logger.debug("Creating Llm bean alias 'gpt-4.1-mini' mapped to provider 'ollama'");
        // Using the Kotlin constructor we identified: (name, provider, model)
        return new Llm(
                "gpt-4.1-mini", // The 'fake' name to satisfy the validator
                "ollama",       // provider
                chatModel       // The actual Spring AI Ollama bean
        );
    }

    @Bean
    @Primary
    public ModelProvider modelProvider(Llm llama3Llm) {
        logger.debug("Creating primary ModelProvider mapped to provided Llm");
        return new ModelProvider() {
            @NotNull @Override
            public Llm getLlm(@NotNull ModelSelectionCriteria criteria) {
                return llama3Llm;
            }

            @NotNull @Override
            public List<ModelMetadata> listModels() {
                return List.of(llama3Llm);
            }

            @NotNull @Override
            public List<String> listModelNames(@NotNull Class<? extends AiModel<?>> modelClass) {
                // Return all aliases to ensure the validator finds what it needs
                return List.of("gpt-4.1-mini", "llama3:latest", "llama3");
            }

            @NotNull @Override
            public List<String> listRoles(@NotNull Class<? extends AiModel<?>> modelClass) {
                return List.of("chat", "ranker", "summarizer");
            }

            @NotNull @Override public String infoString(@NotNull Boolean verbose, int indent) {
                return "Ollama Forced Provider (Mapped to gpt-4.1-mini)";
            }

            @NotNull @Override public EmbeddingService getEmbeddingService(@NotNull ModelSelectionCriteria criteria) {
                throw new UnsupportedOperationException("EmbeddingService not implemented by this provider");
            }
        };
    }
}
