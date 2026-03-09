package com.infinite.newsAIEmbael.agent;

import com.infinite.newsAIEmbael.tools.ArticleFetcher;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.AchievesGoal;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Agent(
        name = "news-agent",
        description = "A news analyst agent capable of summarizing and comparing news reports from HTML sources."
)
@Component
public class NewsAgentImpl implements NewsAgent {

    private static final Logger logger = LoggerFactory.getLogger(NewsAgentImpl.class);
    private final ChatModel chatModel;
    private final ArticleFetcher fetcher;

    public NewsAgentImpl(ChatModel chatModel, ArticleFetcher fetcher) {
        this.chatModel = chatModel;
        this.fetcher = fetcher;
    }

    @Override
    @AchievesGoal(description = "Summarize the provided HTML news file")
    public String summarize(String fileName) {
        logger.info("Agent summarizing: {}", fileName);
        String content = fetcher.fetch(fileName);
        // Using the 2026 Spring AI call method
        return chatModel.call("Summarize this news report concisely: " + content);
    }

    @Override
    @AchievesGoal(description = "Compare two HTML news reports for differences")
    public String compare(String file1, String file2) {
        logger.info("Agent comparing: {} and {}", file1, file2);
        String content1 = fetcher.fetch(file1);
        String content2 = fetcher.fetch(file2);
        return chatModel.call(String.format(
                "Compare these two reports. Highlight key differences and similarities:\n1: %s\n2: %s",
                content1, content2));
    }
}
