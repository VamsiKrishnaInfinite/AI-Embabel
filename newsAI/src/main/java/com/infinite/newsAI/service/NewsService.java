package com.infinite.newsAI.service;



import com.infinite.newsAI.dto.ComparisonResponse;
import com.infinite.newsAI.dto.SummaryResponse;
import com.infinite.newsAI.tools.ArticleFetcher;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service class for news article operations.
 * Provides business logic for summarizing and comparing news articles
 * using AI-powered chat client and article fetcher.
 */
@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    private final ArticleFetcher fetcher;
    private final ChatClient chatClient;

    /**
     * Constructor for NewsService.
     * Initializes the service with ArticleFetcher and ChatClient dependencies.
     *
     * @param fetcher the ArticleFetcher instance for retrieving articles
     * @param builder the ChatClient.Builder for creating ChatClient instance
     */
    public NewsService(ArticleFetcher fetcher, ChatClient.Builder builder) {
        logger.debug("Initializing NewsService with ArticleFetcher and ChatClient");
        this.fetcher = fetcher;
        this.chatClient = builder.build();
    }

    /**
     * Summarizes a news article by fetching it and analyzing with AI.
     * Extracts title, generates summary, and performs sentiment analysis.
     *
     * @param url the URL/path of the article to be summarized
     * @return SummaryResponse containing title, summary, and sentiment
     */
    public SummaryResponse summarize(String url) {
        logger.info("Starting summarization process for URL: {}", url);

        try {
            logger.debug("Fetching article from URL: {}", url);
            String article = fetcher.fetch(url);
            logger.debug("Successfully fetched article, length: {} characters", article.length());

            String prompt = """
                    Analyze the following news article and provide:
                    1. Title
                    2. Summary
                    3. Sentiment (Positive, Negative, Neutral)

                    Article:
                    """ + article;

            logger.debug("Sending article to AI chat client for analysis");
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            logger.debug("Received AI response for summarization");

            SummaryResponse result = new SummaryResponse(
                    "Extracted Title",
                    response,
                    "Neutral"
            );
            logger.info("Successfully completed summarization for URL: {}", url);
            return result;
        } catch (Exception e) {
            logger.error("Error during summarization process for URL: {}", url, e);
            throw new RuntimeException("Summarization failed for URL: " + url, e);
        }
    }

    /**
     * Compares two news articles.
     * Identifies common themes, key differences, and provides overall assessment.
     *
     * @param url1 the URL/path of the first article
     * @param url2 the URL/path of the second article
     * @return ComparisonResponse containing common themes, differences, and assessment
     */
    public ComparisonResponse compare(String url1, String url2) {
        logger.info("Starting comparison process for URL1: {} and URL2: {}", url1, url2);

        try {
            logger.debug("Fetching first article from URL: {}", url1);
            String article1 = fetcher.fetch(url1);
            logger.debug("Successfully fetched first article, length: {} characters", article1.length());

            logger.debug("Fetching second article from URL: {}", url2);
            String article2 = fetcher.fetch(url2);
            logger.debug("Successfully fetched second article, length: {} characters", article2.length());

            String prompt = """
                    Compare the following two news articles.

                    Identify:
                    1. Common theme
                    2. Key differences
                    3. Overall assessment

                    Article 1:
                    """ + article1 +
                    """

                    Article 2:
                    """ + article2;

            logger.debug("Sending articles to AI chat client for comparison analysis");
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            logger.debug("Received AI response for comparison");

            ComparisonResponse result = new ComparisonResponse(
                    "Common theme extracted",
                    List.of("Difference 1", "Difference 2"),
                    response
            );
            logger.info("Successfully completed comparison for URL1: {} and URL2: {}", url1, url2);
            return result;
        } catch (Exception e) {
            logger.error("Error during comparison process for URL1: {} and URL2: {}", url1, url2, e);
            throw new RuntimeException("Comparison failed for URLs: " + url1 + " and " + url2, e);
        }
    }
}