---
title: "NewsAI — Quick Start"
authors:
  - Vamsi Kosaraju
date: 2026-03-03
---

# 🚀 NewsAI — Quick Start

Summary
- NewsAI is a Spring Boot project providing news article summarization and comparison features.
- Two modules in this workspace:
  - `newsAI` — the main Spring Boot app that accepts article URLs and returns structured JSON DTOs.
  - `newsAI-embabel` — an Embabel agent module that processes local classpath HTML files and delegates to a ChatModel (e.g., Ollama). This module uses a pluggable agent design (interface + implementation) and extra configuration to work with model aliases.

Table of contents
- Required software
- Quick build & run (clear, unambiguous steps)
- Install & run Ollama + pull `llama3` model (concrete steps)
- Endpoints with exact curl examples (for both modules and explicit ports)
- How `newsAI-embabel` improves on `newsAI` (concise comparison)
- Troubleshooting

Important note about ports (clarified)
- This README uses the *explicit* ports discovered in the attached Postman collections:
  - `newsAI` (main app): default port **8081** in the example collections
  - `newsAI-embabel` (Embabel agent): default port **8080** in the example collections
- You can change a module's port at runtime via `--server.port=<PORT>` or in `application.properties`. All curl examples below use the explicit port numbers above to avoid ambiguity.

Requirements
- Java 17+ (or the JDK required by your project)
- Maven (or use the included `mvnw` / `mvnw.cmd` wrappers)
- (Optional, required for Embabel agent) Ollama with a local model (see instructions below)

Quick build & run (PowerShell)

1) Build & run the main `newsAI` app (port 8081 in examples)

```powershell
cd C:\Users\vamsiko\Downloads\newsAI\newsAI
# Build
.\mvnw.cmd clean package -DskipTests
# Run (explicit port used here to match examples)
java -jar target\newsAI-0.0.1-SNAPSHOT.jar --server.port=8081
# or (development):
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

2) Build & run `newsAI-embabel` (port 8080 in examples)

```powershell
cd C:\Users\vamsi\Downloads\newsAI\newsAI-embabel
# Build
mvn clean package -DskipTests
# Run (explicit port)
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
```

Install & run Ollama and pull the `llama3` model (Windows-focused, concrete steps)

1) Install Ollama
- Follow official instructions at https://ollama.com/ for the latest installer. On Windows you can download the installer from the website and run it.

2) Verify `ollama` command is available

```powershell
# Verify CLI
ollama --version
ollama list
```

3) Pull the `llama3` model (example model name used by this repo)

```powershell
# Pull model (replace 'llama3' with the exact model name you intend to use if needed)
ollama pull llama3
# Confirm it's available
ollama list
```

4) Run the model locally (start a local server process for the model if required)

```powershell
# Run the model interactively or as a background process depending on your setup
# Example (interactive):
ollama run llama3
# If your version of Ollama supports daemon/serve, use that to make an HTTP endpoint available to Spring AI
# ollama serve (if supported by your installed version)
```

Notes about model aliases and `EmbabelAiConfig`
- The `newsAI-embabel` module registers alias names such as `gpt-4.1-mini`, `llama3:latest`, and `llama3` to satisfy a validator used by the Embabel AgentPlatform. If your local Ollama model uses a different name, either:
  - Pull the model with an alias that matches one of the names above (if your Ollama version supports this), or
  - Update `newsAI-embabel/src/main/java/.../EmbabelAiConfig.java` to add the alias that matches your model name.

Endpoints (explicit curl examples)

A. `newsAI` module (JSON responses)
- Base: POST http://localhost:8081/news
- Summarize — POST /news/summarize
  - Accepts form data: `url` (the article URL)

```powershell
curl -X POST "http://localhost:8081/news/summarize" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url=https://example.com/article.html"
```

- Compare — POST /news/compare
  - Accepts form data: `url1` and `url2`

```powershell
curl -X POST "http://localhost:8081/news/compare" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url1=https://example.com/article1.html" `
  --data "url2=https://example.com/article2.html"
```

B. `newsAI-embabel` module (agent — plain text responses)
- Base: GET http://localhost:8080/news
- Summarize local resource — GET /news/summarize?file=<filename>

```bash
curl "http://localhost:8080/news/summarize?file=uber.html"
```

- Compare two local resources — GET /news/compare?file1=<f1>&file2=<f2>

```bash
curl "http://localhost:8080/news/compare?file1=uber.html&file2=uber2.html"
```

How `newsAI-embabel` improves on `newsAI` (concise comparison)

- Architecture
  - newsAI: a single Spring service handles fetching, prompting, and returning results.
  - newsAI-embabel: introduces an explicit `NewsAgent` interface and `NewsAgentImpl`. That separation enables clearer responsibilities, easier testing, and pluggable agent implementations.

- Model provider & validator handling
  - newsAI: uses a ChatClient builder and a direct ChatModel approach.
  - newsAI-embabel: supplies a `ModelProvider` and alias mapping (`gpt-4.1-mini`, `llama3`), which avoids validator errors when working with non-standard model names and improves flexibility when swapping models.

- Resource handling & logging
  - newsAI-embabel improves the `ArticleFetcher` with clearer logging, robust resource path resolution, and consistent error messages; this makes debugging and local testing easier.

- Documentation & observability
  - The Embabel module includes JavaDoc, SLF4J logs on method entry/exit, and better exception messages — all of which help maintainability and production support.

Collectively these changes make the Embabel module easier to run locally with different LLM providers, simpler to test, and more robust in the presence of model aliasing constraints.



```toml
# newsai.manifest.toml
name = "NewsAI"
version = "0.0.1"
modules = ["newsAI", "newsAI-embabel"]
ports = { newsAI = 8081, embabel = 8080 }
models = ["llama3", "gpt-4.1-mini"]
maintainer = "Vamsi Kosaraju <vamsiko@infinite.com>"
```



NewsAI — Summarize & Compare News Reports
```

Troubleshooting (explicit, unambiguous guidance)
- If the Embabel app fails at startup with a ChatModel bean error:
  1. Confirm `ollama` is installed and `ollama list` shows the model.
  2. Ensure the model name matches one of the aliases in `EmbabelAiConfig` or update `EmbabelAiConfig`.
- If endpoints return 404:
  - Confirm the app process is running and listening on the port shown in your run command.
- Want to run both modules at the same time? Use explicit ports when starting each jar (see Quick run commands above).

Helpful Postman / collection files
- I included two Postman collections in the workspace (copy to Postman):
  - `spring-ai-demo.json` — example collection covering a summarize request (uses port 8081 in the collection)
  - `spring-embabble.json` — example collection for Embabel agent endpoints (uses port 8080 in the collection)

