package com.infinite.newsAIEmbael.controller;

import com.infinite.newsAIEmbael.agent.NewsAgent;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller exposing /news endpoints and delegating to NewsAgent.
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    private final NewsAgent newsAgent;

    public NewsController(NewsAgent newsAgent) {
        logger.debug("Creating NewsController with NewsAgent");
        this.newsAgent = newsAgent;
    }

    @GetMapping("/summarize")
    public String summarize(@RequestParam String file) {
        logger.info("Received summarize request for file: {}", file);
        logger.debug("Delegating summarize to NewsAgent.summarize for file: {}", file);
        String result = newsAgent.summarize(file);
        logger.info("Summarize request completed for file: {}", file);
        return result;
    }

    @GetMapping("/compare")
    public String compare(@RequestParam String file1, @RequestParam String file2) {
        logger.info("Received compare request for files: {} and {}", file1, file2);
        logger.debug("Delegating compare to NewsAgent.compare for files: {} and {}", file1, file2);
        String result = newsAgent.compare(file1, file2);
        logger.info("Compare request completed for files: {} and {}", file1, file2);
        return result;
    }
}
