package com.infinite.newsAIEmbael.tools;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component for fetching HTML articles from classpath and converting to plain text for processing.
 */
@Component
public class ArticleFetcher {

    private static final Logger logger = LoggerFactory.getLogger(ArticleFetcher.class);

    public String fetch(String fileName) {
        logger.info("Fetching article resource: {}", fileName);
        try {
            // Check if user provided extension, if not add .html
            String resourcePath = fileName.endsWith(".html") ? fileName : fileName + ".html";
            var resource = new ClassPathResource(resourcePath);
            logger.debug("Resolved resource path: {}", resourcePath);

            try (Scanner scanner = new Scanner(resource.getInputStream(), StandardCharsets.UTF_8)) {
                String content = scanner.useDelimiter("\\A").next();
                // Strip HTML tags to provide clean text to the LLM
                String cleaned = content.replaceAll("<[^>]*>", " ").replaceAll("\\s+", " ").trim();
                logger.debug("Fetched article text length: {} characters", cleaned.length());
                return cleaned;
            }
        } catch (Exception e) {
            logger.error("Failed to fetch HTML article: {}", fileName, e);
            throw new RuntimeException("Failed to fetch HTML article: " + fileName, e);
        }
    }
}
