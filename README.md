# NewsAI — Quick Start (Updated: 2026-03-02)

Overview
- NewsAI is a Spring Boot project that provides news article summarization and comparison features.
- The workspace contains two related modules:
  - `newsAI` — main Spring Boot application exposing JSON endpoints that accept article URLs and return structured DTOs (`SummaryResponse`, `ComparisonResponse`).
  - `newsAI-embabel` — an Embabel-enabled module (agent) that exposes endpoints which accept classpath HTML file names and return textual analysis produced by an agent and a configured ChatModel (e.g., Ollama).

Contents of this README
- Prerequisites
- How to run (both modules)
- Ollama (local LLM) notes and setup
- Example curl requests for every endpoint (both modules)
- Troubleshooting

Prerequisites
- Java 17+ (or the JDK version your project requires)
- Maven (or use the included wrapper where available)
- (Optional for Embabel agent) Ollama installed and a local model available if you want to run the `newsAI-embabel` module with a local ChatModel implementation

Ollama (local LLM) — Notes and Example Setup
- The `newsAI-embabel` module expects a ChatModel bean that can connect to a model provider. Locally you can use Ollama.
- Install Ollama (official instructions): https://ollama.com/
- Pull or install a model you want to use. Example (check Ollama docs for exact model names):

  # Example (run in your shell; replace <model-name> with actual model)
  ollama pull <model-name>
  ollama list

- Make sure Ollama is running and accessible to Spring AI/Ollama integration. Depending on your setup you may need to run an Ollama daemon or ensure the model is available locally.
- The project registers alias names such as `gpt-4.1-mini`, `llama3:latest`, and `llama3` in `newsAI-embabel` to satisfy an Embabel validator. Ensure the model you use is available under (or mapped to) one of those names, or update `EmbabelAiConfig` to use the model alias you have locally.

Build and Run (Windows PowerShell examples)

1) Build and run the main `newsAI` app

Open PowerShell and run:

```powershell
cd C:\Users\vamsiko\Downloads\newsAI\newsAI
.\mvnw.cmd clean package -DskipTests
# Run the packaged jar
java -jar target\newsAI-0.0.1-SNAPSHOT.jar
# Or run directly during development
.\mvnw.cmd spring-boot:run
```

Default HTTP port: 8080 (unless overridden in `application.properties`).

2) Build and run `newsAI-embabel` (Embabel agent module)

If you have Maven installed, run from the `newsAI-embabel` directory. (There is no mvnw wrapper in that submodule in this workspace, so use a local Maven install or run from a parent aggregator if configured.)

```powershell
cd C:\Users\vamsiko\Downloads\newsAI\newsAI-embabel
mvn clean package -DskipTests
# Or run in place
mvn spring-boot:run
```

Before starting `newsAI-embabel`, ensure your ChatModel provider (e.g., Ollama) is installed and reachable; otherwise the application may fail to create the required beans.

Endpoints and Example Requests

A. `newsAI` module
- Base path: POST http://localhost:8080/news/*
- These endpoints expect form-encoded POST body parameters (application/x-www-form-urlencoded).

1) Summarize — POST /news/summarize
- Description: Summarize an article provided by a URL (or path depending on ArticleFetcher implementation).

Example curl (PowerShell):

```powershell
curl -X POST "http://localhost:8080/news/summarize" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url=https://example.com/article.html"
```

Example response (JSON):

```json
{
  "title": "Extracted Title",
  "summary": "AI generated summary text...",
  "sentiment": "Neutral"
}
```

2) Compare — POST /news/compare
- Description: Compare two articles by URL and return common themes, key differences and an AI assessment.

Example curl (PowerShell):

```powershell
curl -X POST "http://localhost:8080/news/compare" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url1=https://example.com/article1.html" `
  --data "url2=https://example.com/article2.html"
```

Example response (JSON):

```json
{
  "commonTheme": "Politics / Policy",
  "differences": ["Perspective A emphasizes X", "Perspective B emphasizes Y"],
  "overallAssessment": "AI analysis text..."
}
```

B. `newsAI-embabel` module
- Base path: GET http://localhost:8080/news/*
- These endpoints accept `file` (or `file1`/`file2`) query parameters that are classpath resource names (e.g., `uber.html`, `uber2.html`).

1) Summarize local classpath file — GET /news/summarize

```bash
curl "http://localhost:8080/news/summarize?file=uber.html"
```

Response: plain text string produced by the agent (e.g., a concise summary).

2) Compare two local classpath files — GET /news/compare

```bash
curl "http://localhost:8080/news/compare?file1=uber.html&file2=uber2.html"
```

Response: plain text string (comparison/analysis) returned by the agent.

Troubleshooting & Tips
- If you see bean creation errors when starting `newsAI-embabel`, verify your ChatModel/Ollama setup and the model aliasing in `EmbabelAiConfig`.
- If the port is already in use, start the app with a different port:

```powershell
# Run jar with different port
java -jar target\newsAI-0.0.1-SNAPSHOT.jar --server.port=9090

# Or when using mvn spring-boot:run
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

- Logging: The project uses SLF4J. Adjust logging levels in `application.properties` to see more/less detail.

- Local testing: For quick local tests you can place sample HTML files in `src/main/resources` (module depending) and use the Embabel endpoints to fetch them by name.



