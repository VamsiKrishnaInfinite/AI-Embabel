package com.infinite.newsAIEmbael;


import com.embabel.agent.config.annotation.EnableAgents;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main application class for Embabel-enabled NewsAI; sets up environment and starts Spring Boot.
 */
@SpringBootApplication
@EnableAgents
public class NewsAiEmbabelApplication {

    private static final Logger logger = LoggerFactory.getLogger(NewsAiEmbabelApplication.class);

    public static void main(String[] args) {
        logger.info("Starting NewsAI-Embabel application...");
        System.setProperty("spring.main.allow-bean-definition-overriding", "true");
        SpringApplication.run(NewsAiEmbabelApplication.class, args);
        logger.info("NewsAI-Embabel application started successfully");
    }
}
