package com.infinite.newsAIEmbael.dto;

/**
 * Data Transfer Object for article summary results.
 * Contains the summarized information extracted from a news article.
 *
 * @param title the title of the article
 * @param summary the summarized content of the article
 * @param sentiment the sentiment analysis result (Positive, Negative, or Neutral)
 */
public record SummaryResponse(
        String title,
        String summary,
        String sentiment
) {}