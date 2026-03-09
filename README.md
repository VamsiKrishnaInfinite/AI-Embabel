```text
___  ___    _______ .___  ___. .______        ___      .______    _______  __         ___  ___
/  / /  /   |   ____||   \/   | |   _  \      /   \     |   _  \  |   ____||  |        \  \ \  \
/  / /  /   |  |__   |  \  /  | |  |_)  |    /  ^  \    |  |_)  | |  |__   |  |         \  \ \  \
<  < <  <   |   __|  |  |\/|  | |   _  <    /  /_\  \   |   _  <  |   __|  |  |          >  > >  >
\  \ \  \   |  |____ |  |  |  | |  |_)  |  /  _____  \  |  |_)  | |  |____ |  `----.    /  / /  /
\__\ \__\   |_______||__|  |__| |______/  /__/     \__\ |______/  |_______||_______|   /__/ /__/
                                                                                      - Demo by 
                                                                                     __  __       __  __ 
                                                                                    / /_/ /  __  / /_/ / 
                                                                                   / ,< / | / / / ,< /  
                                                                                  /_/|_| |__/  /_/|_|   
```

# 🚀 NewsAI — Intelligent News Summarization & Comparison

> A Spring Boot project providing AI-powered news article summarization and comparison features with support for multiple LLM providers (Ollama, OpenAI, Claude).

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Repository Structure](#repository-structure)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Module Details](#module-details)
- [API Endpoints](#api-endpoints)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Usage Examples](#usage-examples)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## 📖 Overview

**NewsAI** is a dual-module Spring Boot application for intelligent news processing:

- **newsAI-springAI**: Core service that fetches and summarizes news articles from URLs using ChatClient
- **newsAI-embable**: Advanced agent-based module using Embabel platform with pluggable AI providers (Ollama, GPT models, etc.)

Both modules provide REST APIs for:
- 📰 **Summarize**: Extract concise summaries from news articles
- 🔄 **Compare**: Analyze and compare multiple news sources side-by-side

**Language Composition**: Java (82.9%) | HTML (17.1%)

## 🏗️ Architecture

```
AI-Embabel (Multi-Module Maven Project)
├── newsAI-springAI (Port: 8081)
│   ├── ChatClient-based summarization
│   ├── Direct LLM integration
│   └── Simple, lightweight architecture
│
├── newsAI-embable (Port: 8080)
│   ├── Embabel Agent Platform integration
│   ├── Pluggable provider architecture
│   ├── Model aliasing & validation
│   └── Enhanced error handling & logging
│
└── Postman Collections
    ├── spring-ai-demo.json
    └── spring-embabble.json
```

### Design Philosophy

- **Separation of Concerns**: Explicit `NewsAgent` interface with implementations for different providers
- **Pluggable Architecture**: Swap LLM providers without code changes
- **Model Provider Abstraction**: Unified interface for different model types (llama, gpt-4, etc.)
- **Comprehensive Logging**: SLF4J with method-level entry/exit logging
- **Robust Error Handling**: Detailed exception messages for debugging

## 📁 Repository Structure

```
VamsiKrishnaInfinite/AI-Embabel/
├── newsAI-springAI/                    # Spring AI module (lightweight)
│   ├── src/
│   │   ├── main/java/                  # Core application code
│   │   │   └── com/newsai/
│   │   │       ├── controller/         # REST endpoints
│   │   │       ├── service/            # Business logic
│   │   │       ├── dto/                # Data transfer objects
│   │   │       └── config/             # Spring configuration
│   │   ├── test/java/                  # Unit tests
│   │   └── resources/
│   │       └── application.properties  # Configuration
│   ├── pom.xml                         # Maven dependencies
│   └── README.md
│
├── newsAI-embable/                     # Embabel agent module (advanced)
│   ├── src/
│   │   ├── main/java/                  # Agent implementation
│   │   │   └── com/newsai/embabel/
│   │   │       ├── agent/              # NewsAgent interface & implementations
│   │   │       ├── controller/         # REST endpoints
│   │   │       ├── fetcher/            # Article fetching logic
│   │   │       ├── provider/           # Model provider abstraction
│   │   │       ├── config/             # Spring & Embabel configuration
│   │   │       └── dto/                # Data models
│   │   ├── test/java/                  # Unit & integration tests
│   │   ├── resources/
│   │   │   ├── application.properties  # Configuration
│   │   │   └── articles/               # Local HTML test files
│   │   └── pom.xml
│   └── README.md
│
├── .postman/                           # Postman collection directory
├── spring-ai-demo.json                 # Postman collection: newsAI-springAI
├── spring-embabble.json                # Postman collection: newsAI-embable
├── .gitignore                          # Git ignore rules
├── README.md                           # This file
└── newsai.manifest.toml                # Project manifest

```

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Framework** | Spring Boot | 3.x+ |
| **Language** | Java | 17+ |
| **Build Tool** | Maven | 3.8+ |
| **AI Framework** | Spring AI | Latest |
| **Agent Platform** | Embabel | Integration-ready |
| **LLM Providers** | Ollama, OpenAI, Claude | Compatible |
| **Local LLM** | Ollama | With llama3 model |
| **API Docs** | REST (Spring MVC) | JSON responses |
| **Testing** | JUnit 5, Mockito | Included |

## 📦 Requirements

- **Java 17 or higher** (JDK 21 recommended)
- **Maven 3.8+** (or use included `mvnw` / `mvnw.cmd`)
- **Ollama** (optional, required for Embabel local inference)
- **Git** (for cloning the repository)

### Optional: For Local LLM Support
- **Ollama CLI**: https://ollama.com/
- **llama3 Model**: Or any compatible Ollama model

## 🚀 Quick Start

### 1️⃣ Clone & Navigate

```bash
git clone https://github.com/VamsiKrishnaInfinite/AI-Embabel.git
cd AI-Embabel
```

### 2️⃣ Build Both Modules

```powershell
# Using Maven wrapper (Windows)
cd newsAI-springAI
.\mvnw.cmd clean package -DskipTests

cd ../newsAI-embable
.\mvnw.cmd clean package -DskipTests
```

### 3️⃣ Run Modules

**Terminal 1 - NewsAI (Port 8081):**
```powershell
cd newsAI-springAI
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
# or: java -jar target\newsAI-0.0.1-SNAPSHOT.jar --server.port=8081
```

**Terminal 2 - NewsAI-Embabel (Port 8080):**
```powershell
cd newsAI-embable
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
# or: mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
```

### 4️⃣ Verify Services

```powershell
# Test newsAI-springAI
curl -X POST "http://localhost:8081/news/summarize" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url=https://example.com/article.html"

# Test newsAI-embable
curl "http://localhost:8080/news/summarize?file=uber.html"
```

## 📦 Module Details

### **newsAI-springAI** �� Lightweight, ChatClient-Based

**Purpose**: Simple, direct LLM integration for article summarization and comparison

**Key Components**:
- `NewsController`: REST endpoints for `/news/summarize` and `/news/compare`
- `NewsService`: Business logic using Spring AI ChatClient
- `ArticleFetcher`: Utility to fetch and parse HTML articles
- `NewsDTO`: Structured response objects (title, summary, comparison_result)

**Characteristics**:
- ✅ Lightweight & fast
- ✅ Works with OpenAI, Azure OpenAI, Ollama
- ✅ Minimal configuration
- ✅ Good for quick prototyping

**Ports**: 8081 (default in examples)

---

### **newsAI-embable** — Advanced Agent-Based

**Purpose**: Enterprise-grade agent platform with pluggable providers and robust error handling

**Key Components**:
- `NewsAgent` interface: Abstract agent contract
- `NewsAgentImpl`: Core implementation
- `ModelProvider`: Provider abstraction layer
- `ArticleFetcher`: Enhanced with logging and path resolution
- `EmbabelAiConfig`: Model aliasing (gpt-4.1-mini, llama3, etc.)
- `NewsController`: Agent-delegated endpoints

**Characteristics**:
- ✅ Agent-based architecture (Embabel platform)
- ✅ Model provider abstraction & aliasing
- ✅ Comprehensive logging (SLF4J)
- ✅ Robust error handling
- ✅ Local file processing support
- ✅ Easier testing & extensibility

**Ports**: 8080 (default in examples)

---

### **Comparison: newsAI vs newsAI-embable**

| Aspect | newsAI-springAI | newsAI-embable |
|--------|-----------------|---|
| **Architecture** | Direct ChatClient | Agent-based (Embabel) |
| **Model Provider** | ChatClient builder | ModelProvider interface |
| **Error Handling** | Basic | Comprehensive with aliasing |
| **Logging** | Standard | Method-level SLF4J |
| **Testing** | Service-level | Agent-level (mockable) |
| **Local Files** | Limited | Full support with path resolution |
| **Use Case** | Quick prototyping | Production deployment |

## 🔌 API Endpoints

### **NewsAI-SpringAI (Port 8081)**

#### Summarize Article from URL
```http
POST /news/summarize
Content-Type: application/x-www-form-urlencoded

url=https://example.com/article.html
```

**Response**: JSON with structured summary
```json
{
  "title": "Article Title",
  "summary": "Concise summary...",
  "source_url": "https://example.com/article.html"
}
```

**cURL Example**:
```bash
curl -X POST "http://localhost:8081/news/summarize" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  --data "url=https://www.example.com/article.html"
```

---

#### Compare Two Articles from URLs
```http
POST /news/compare
Content-Type: application/x-www-form-urlencoded

url1=https://example.com/article1.html
url2=https://example.com/article2.html
```

**Response**: Comparative analysis
```json
{
  "article1_title": "Title 1",
  "article2_title": "Title 2",
  "comparison_result": "Analysis of differences..."
}
```

**cURL Example**:
```bash
curl -X POST "http://localhost:8081/news/compare" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  --data "url1=https://example.com/article1.html&url2=https://example.com/article2.html"
```

---

### **NewsAI-Embabel (Port 8080)**

#### Summarize Local Article
```http
GET /news/summarize?file=<filename>
```

**Response**: Plain text agent response

**cURL Example**:
```bash
curl "http://localhost:8080/news/summarize?file=uber.html"
```

---

#### Compare Two Local Articles
```http
GET /news/compare?file1=<filename1>&file2=<filename2>
```

**Response**: Plain text comparative analysis

**cURL Example**:
```bash
curl "http://localhost:8080/news/compare?file1=uber.html&file2=tesla.html"
```

---

## 🔧 Installation & Setup

### **Option 1: Build & Run Locally**

```powershell
# Clone repository
git clone https://github.com/VamsiKrishnaInfinite/AI-Embabel.git
cd AI-Embabel

# Build newsAI-springAI
cd newsAI-springAI
.\mvnw.cmd clean package -DskipTests
cd ..

# Build newsAI-embable
cd newsAI-embable
.\mvnw.cmd clean package -DskipTests
cd ..
```

### **Option 2: Run with Ollama (For newsAI-embable)**

#### Step 1: Install Ollama
```powershell
# Download from https://ollama.com/ and run installer
# Verify installation
ollama --version
```

#### Step 2: Pull Model
```powershell
ollama pull llama3
ollama list  # Confirm model is available
```

#### Step 3: Start Ollama (if needed)
```powershell
# On some versions, Ollama runs as a service automatically
# If not, start it explicitly:
ollama serve
# Or run interactively:
ollama run llama3
```

#### Step 4: Run newsAI-embable
```powershell
cd newsAI-embable
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
```

---

## ⚙️ Configuration

### Port Configuration

Change port at runtime:

```bash
# newsAI-springAI (default 8081)
java -jar newsAI-0.0.1-SNAPSHOT.jar --server.port=9000

# newsAI-embable (default 8080)
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9001"
```

Or edit `application.properties`:
```properties
server.port=8081
```

### Model Provider Configuration (newsAI-embable)

**File**: `newsAI-embable/src/main/java/.../EmbabelAiConfig.java`

Register model aliases:
```java
// Supported aliases in validator
// - gpt-4.1-mini
// - llama3:latest
// - llama3
// - others...
```

If using a custom model name:
1. Add your model to `EmbabelAiConfig`
2. Or pull with a matching alias in Ollama

### LLM Provider Configuration (newsAI-springAI)

**File**: `newsAI-springAI/src/main/resources/application.properties`

```properties
# For Ollama
spring.ai.ollama.model=llama3

# For OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.model=gpt-4
```

---

## 💡 Usage Examples

### Example 1: Summarize a Tech Article

```powershell
curl -X POST "http://localhost:8081/news/summarize" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "url=https://techcrunch.com/2024/article"
```

**Expected Output**:
```json
{
  "title": "Latest AI Breakthrough",
  "summary": "Researchers announced a new model achieving...",
  "source_url": "https://techcrunch.com/2024/article"
}
```

---

### Example 2: Compare Two News Sources (Embabel Agent)

```bash
curl "http://localhost:8080/news/compare?file1=reuters_article.html&file2=bbc_article.html"
```

**Expected Output**:
```
Agent Response:
Reuters emphasizes economic impact, while BBC focuses on geopolitical implications...
```

---

### Example 3: Batch Processing with Postman

1. **Import Collection**: Open Postman → Import → Select `spring-ai-demo.json` or `spring-embabble.json`
2. **Run Collection**: Use Postman's Collection Runner
3. **View Results**: Compare outputs across endpoints

---

## 🐛 Troubleshooting

### **Issue**: Embabel startup fails with "ChatModel bean not found"

**Solution**:
```powershell
# Verify Ollama is installed and running
ollama --version
ollama list

# Check if model exists
ollama pull llama3

# Ensure EmbabelAiConfig has matching alias
# Edit newsAI-embable/src/main/java/.../EmbabelAiConfig.java
```

---

### **Issue**: 404 Not Found on endpoints

**Solution**:
```powershell
# Verify correct port
# newsAI-springAI should run on 8081
# newsAI-embable should run on 8080

# Check running processes
netstat -ano | findstr :8081
netstat -ano | findstr :8080

# Restart with explicit port
java -jar target\newsAI-0.0.1-SNAPSHOT.jar --server.port=8081
```

---

### **Issue**: Model name validation errors

**Solution**:
- Check `EmbabelAiConfig.java` for registered model aliases
- Ensure Ollama model name matches a registered alias
- Or pull the model with a matching alias name:
  ```powershell
  ollama pull llama3:latest  # If llama3:latest is registered
  ```

---

### **Issue**: Unable to fetch remote URLs

**Solution**:
- Ensure network connectivity
- Check firewall/proxy settings
- For newsAI-embable, use local HTML files instead:
  ```bash
  curl "http://localhost:8080/news/summarize?file=local_article.html"
  ```

---

## 🚢 Running Both Modules Simultaneously

**PowerShell (Recommended):**

```powershell
# Terminal 1
cd newsAI-springAI
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# Terminal 2 (separate PowerShell window)
cd newsAI-embable
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"

# Terminal 3 (Testing)
curl "http://localhost:8081/news/summarize?url=..."
curl "http://localhost:8080/news/summarize?file=..."
```

---

## 📚 Postman Collections

**Included Collections**:

1. **`spring-ai-demo.json`** — newsAI-springAI endpoints (port 8081)
   - Summarize article from URL
   - Compare two URLs
   - Pre-configured with example URLs

2. **`spring-embabble.json`** — newsAI-embabel endpoints (port 8080)
   - Summarize local file
   - Compare local files
   - Agent-based responses

**Import Steps**:
1. Open Postman
2. Click **Import** → Select JSON file
3. Choose environment/collection
4. Start testing!

---

## 📖 Project Manifest

**newsai.manifest.toml**:
```toml
name = "NewsAI"
version = "0.0.1"
modules = ["newsAI-springAI", "newsAI-embabel"]
ports = { springAI = 8081, embabel = 8080 }
models = ["llama3", "gpt-4.1-mini"]
maintainer = "Vamsi Kosaraju <vamsiko@infinite.com>"
```

---

## 🤝 Contributing

Contributions are welcome! Please:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/awesome-feature`)
3. **Commit** changes (`git commit -am 'Add awesome feature'`)
4. **Push** to branch (`git push origin feature/awesome-feature`)
5. **Open** a Pull Request

---

## 📝 License

This project is maintained by **Vamsi Kosaraju** — Contact: `vamsiko@infinite.com`

---

## 🔗 Useful Links

- [Spring AI Documentation](https://spring.io/projects/spring-ai)
- [Ollama Official](https://ollama.com/)
- [Embabel Platform](https://embabel.io/)
- [OpenAI API Docs](https://platform.openai.com/docs)

---

## ❓ FAQ

**Q: Can I use GPT-4 instead of Ollama?**  
A: Yes! Configure `application.properties` with your OpenAI API key in newsAI-springAI. For newsAI-embabel, ensure the model alias is registered in `EmbabelAiConfig`.

**Q: How do I add custom HTML articles for testing?**  
A: Place `.html` files in `newsAI-embable/src/main/resources/articles/` and reference them by filename in the API.

**Q: Can I run both modules on the same port?**  
A: No, use different ports (e.g., 8081 and 8080) to avoid conflicts. Specify with `--server.port=<PORT>`.

**Q: What's the difference between newsAI-springAI and newsAI-embabel?**  
A: See the [Comparison Table](#comparison-newsai-vs-newsai-embable) above. TL;DR: newsAI-springAI is lightweight; newsAI-embabel is production-ready with agents.

---

**Last Updated**: March 9, 2026  
**Repository**: [VamsiKrishnaInfinite/AI-Embabel](https://github.com/VamsiKrishnaInfinite/AI-Embabel)
