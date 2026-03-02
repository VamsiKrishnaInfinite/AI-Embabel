package com.infinite.newsAI.controller;


import com.infinite.newsAI.dto.ComparisonResponse;
import com.infinite.newsAI.dto.SummaryResponse;
import com.infinite.newsAI.service.NewsService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Controller for news operations.
 * Handles HTTP requests for news article summarization and comparison.
 * Provides endpoints for processing news articles through the NewsService.
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    private final NewsService service;

    /**
     * Constructor for NewsController.
     * Initializes the controller with a NewsService dependency.
     *
     * @param service the NewsService instance to handle business logic
     */
    public NewsController(NewsService service) {
        logger.debug("Initializing NewsController with NewsService");
        this.service = service;
    }

    /**
     * Endpoint to summarize a news article from the provided URL.
     * Processes a single article and returns its summary, title, and sentiment analysis.
     *
     * @param url the URL of the article to be summarized
     * @return SummaryResponse containing the title, summary, and sentiment of the article
     */
    @PostMapping("/summarize")
    public SummaryResponse summarize(@RequestParam String url) {
        logger.info("Received request to summarize article from URL: {}", url);
        try {
            SummaryResponse response = service.summarize(url);
            logger.info("Successfully summarized article from URL: {}", url);
            return response;
        } catch (Exception e) {
            logger.error("Error summarizing article from URL: {}", url, e);
            throw e;
        }
    }

    /**
     * Endpoint to compare two news articles.
     * Compares two articles and returns their common themes, differences, and overall assessment.
     *
     * @param url1 the URL of the first article
     * @param url2 the URL of the second article
     * @return ComparisonResponse containing common themes, differences, and assessment
     */
    @PostMapping("/compare")
    public ComparisonResponse compare(@RequestParam String url1,
                                      @RequestParam String url2) {
        logger.info("Received request to compare articles from URL1: {} and URL2: {}", url1, url2);
        try {
            ComparisonResponse response = service.compare(url1, url2);
            logger.info("Successfully compared articles from URL1: {} and URL2: {}", url1, url2);
            return response;
        } catch (Exception e) {
            logger.error("Error comparing articles from URL1: {} and URL2: {}", url1, url2, e);
            throw e;
        }
    }
}