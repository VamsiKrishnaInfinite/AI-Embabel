package com.infinite.newsAIEmbael.agent;

/**
 * Implementation of NewsAgent that uses ChatModel and ArticleFetcher to summarize and compare HTML files.
 */
@com.embabel.agent.api.annotation.Agent(description = "A news analyst agent capable of summarizing and comparing news reports from HTML sources.")
@org.springframework.stereotype.Component

import com.infinite.newsAIEmbael.tools.ArticleFetcher;
import org.springframework.ai.chat.model.ChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsAgentImpl implements NewsAgent {

    private static final Logger logger = LoggerFactory.getLogger(NewsAgentImpl.class);

    private final ChatModel chatModel;
    private final ArticleFetcher fetcher;

    public NewsAgentImpl(ChatModel chatModel, ArticleFetcher fetcher) {
        logger.debug("Initializing NewsAgentImpl with ChatModel and ArticleFetcher");
        this.chatModel = chatModel;
        this.fetcher = fetcher;
    }

    @Override
    @com.embabel.agent.api.annotation.AchievesGoal(description = "Summarize the provided HTML news file")
    public String summarize(String fileName) {
        logger.info("Agent summarize called for file: {}", fileName);
        String content = fetcher.fetch(fileName);
        logger.debug("Fetched content length for file {}: {}", fileName, content.length());
        logger.debug("Invoking chatModel.call for summarization");
        String result = chatModel.call("Summarize this news report concisely: " + content);
        if (result != null) {
            logger.debug("ChatModel summarize response length: {}", result.length());
        }
        return result;
    }

    @Override
    @com.embabel.agent.api.annotation.AchievesGoal(description = "Compare two HTML news reports for differences")
    public String compare(String file1, String file2) {
        logger.info("Agent compare called for files: {} and {}", file1, file2);
        String content1 = fetcher.fetch(file1);
        String content2 = fetcher.fetch(file2);
        logger.debug("Fetched content lengths: {} -> {}, {} -> {}", file1, content1.length(), file2, content2.length());
        logger.debug("Invoking chatModel.call for comparison");
        String result = chatModel.call(String.format(
                "Compare these two news reports. Highlight key differences and similarities:\nReport 1: %s\nReport 2: %s",
                content1, content2));
        if (result != null) {
            logger.debug("ChatModel compare response length: {}", result.length());
        }
        return result;
    }
}
