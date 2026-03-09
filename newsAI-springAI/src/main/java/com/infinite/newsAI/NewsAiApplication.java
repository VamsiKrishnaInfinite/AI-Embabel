package com.infinite.newsAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class for NewsAI Spring Boot application.
 * This is the entry point for the NewsAI application that provides
 * news article summarization and comparison capabilities.
 */
@SpringBootApplication
public class NewsAiApplication {

    private static final Logger logger = LoggerFactory.getLogger(NewsAiApplication.class);

    /**
     * Main method to start the NewsAI application.
     * Initializes Spring Boot application context and starts the server.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        logger.info("Starting NewsAI application...");
        SpringApplication.run(NewsAiApplication.class, args);
        logger.info("NewsAI application started successfully");
    }

}
