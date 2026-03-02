package com.infinite.newsAI.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Component class for fetching and parsing articles.
 * Reads HTML articles from classpath resources and extracts text content
 * using Jsoup for parsing.
 */
@Component
public class ArticleFetcher {

    private static final Logger logger = LoggerFactory.getLogger(ArticleFetcher.class);

    /**
     * Fetches and parses an article from classpath resources.
     * Reads the HTML file, parses it, and extracts plain text content.
     *
     * @param fileName the name of the file to fetch from classpath
     * @return the extracted text content from the HTML document
     * @throws RuntimeException if the file cannot be read or parsed
     */
    public String fetch(String fileName) {
        logger.info("Starting fetch operation for file: {}", fileName);

        try {
            logger.debug("Loading classpath resource: {}", fileName);
            ClassPathResource resource = new ClassPathResource(fileName);

            InputStream inputStream = resource.getInputStream();
            logger.debug("Successfully obtained input stream for: {}", fileName);

            logger.debug("Parsing HTML document from: {}", fileName);
            Document doc = Jsoup.parse(inputStream, "UTF-8", "");

            String text = doc.text();
            logger.info("Successfully fetched and parsed article from: {}. Content length: {} characters",
                    fileName, text.length());

            return text;

        } catch (Exception e) {
            logger.error("Failed to read or parse article from file: {}", fileName, e);
            throw new RuntimeException("Failed to read local article: " + fileName, e);
        }
    }
}