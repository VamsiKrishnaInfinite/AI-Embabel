package com.infinite.newsAIEmbael.agent;

/**
 * Agent interface describing capabilities to summarize and compare classpath HTML news files.
 */
public interface NewsAgent {
    /**
     * Summarize the specified classpath HTML file and return a concise textual summary.
     *
     * @param fileName the classpath resource name (with or without .html)
     * @return a concise summary of the article
     */
    String summarize(String fileName);

    /**
     * Compare two classpath HTML files and return analysis of differences and similarities.
     *
     * @param file1 the first classpath resource name
     * @param file2 the second classpath resource name
     * @return a textual comparison highlighting similarities and differences
     */
    String compare(String file1, String file2);
}
