package com.infinite.newsAI.dto;
import java.util.List;

/**
 * Data Transfer Object for article comparison results.
 * Contains the comparison analysis between two news articles.
 *
 * @param commonTheme the common theme or topic identified between the two articles
 * @param differences a list of key differences found between the articles
 * @param overallAssessment the overall assessment and analysis of the comparison
 */
public record ComparisonResponse(
        String commonTheme,
        List<String> differences,
        String overallAssessment
) {}