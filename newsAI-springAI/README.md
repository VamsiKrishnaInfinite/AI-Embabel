# NewsAI Spring Boot Application

This is a Spring Boot application that leverages Spring AI and Ollama to provide news article summarization and comparison services. The application uses the Llama3 model via Ollama for natural language processing tasks.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 21**: The application requires Java 21. You can download it from the [official Oracle website](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or use a package manager like SDKMAN.
- **Maven**: For building and running the project. Maven 3.6+ is recommended. You can download it from [Apache Maven](https://maven.apache.org/download.cgi). Alternatively, the project includes Maven wrapper scripts (`mvnw` and `mvnw.cmd`) that can be used on Unix-like systems and Windows respectively.
- **Ollama**: The application uses Ollama to run the Llama3 language model locally. Download and install Ollama from [ollama.ai](https://ollama.ai/). After installation, pull the Llama3 model by running:
  ```
  ollama pull llama3
  ```

## Installation and Setup

1. **Clone or Download the Project**:
   - Clone the repository or download the project files to your local machine.

2. **Navigate to the Project Directory**:
   - Open a terminal or command prompt and navigate to the root directory of the project (where `pom.xml` is located).

3. **Ensure Ollama is Running**:
   - Start Ollama if it's not already running. You can do this by opening a terminal and running:
     ```
     ollama serve
     ```
   - Verify that the Llama3 model is available by running:
     ```
     ollama list
     ```
     You should see `llama3:latest` in the list.

## Running the Application

### Option 1: Using Maven Wrapper (Recommended)

1. **Build the Application**:
   - Run the following command to clean and compile the project:
     ```
     ./mvnw clean compile
     ```
     (On Windows, use `mvnw.cmd clean compile`)

2. **Run the Application**:
   - Start the Spring Boot application:
     ```
     ./mvnw spring-boot:run
     ```
     (On Windows, use `mvnw.cmd spring-boot:run`)

### Option 2: Using System Maven

If you have Maven installed system-wide:

1. **Build the Application**:
   ```
   mvn clean compile
   ```

2. **Run the Application**:
   ```
   mvn spring-boot:run
   ```

### Option 3: Build and Run JAR

1. **Package the Application**:
   ```
   ./mvnw clean package
   ```
   (This creates a JAR file in the `target` directory)

2. **Run the JAR**:
   ```
   java -jar target/newsAI-0.0.1-SNAPSHOT.jar
   ```

## Accessing the Application

Once the application is running, it will start on port 8081. You can access the API endpoints using tools like `curl`, Postman, or any HTTP client.

### API Endpoints

- **Summarize Article**:
  - **Endpoint**: `POST http://localhost:8081/news/summarize?url=<article_url>`
  - **Description**: Summarizes a news article from the provided URL.
  - **Example**:
    ```
    curl -X POST "http://localhost:8081/news/summarize?url=https://example.com/news-article"
    ```

- **Compare Articles**:
  - **Endpoint**: `POST http://localhost:8081/news/compare?url1=<url1>&url2=<url2>`
  - **Description**: Compares two news articles and provides common themes, differences, and an overall assessment.
  - **Example**:
    ```
    curl -X POST "http://localhost:8081/news/compare?url1=https://example.com/article1&url2=https://example.com/article2"
    ```

## Configuration

The application uses the following key configurations (defined in `src/main/resources/application.properties`):

- **Server Port**: 8081
- **Ollama Base URL**: http://localhost:11434
- **Model**: llama3:latest
- **Temperature**: 0.7

You can modify these settings by editing the `application.properties` file or by setting environment variables.

## Troubleshooting

- **Ollama Connection Issues**: Ensure Ollama is running and accessible at `http://localhost:11434`. Check the Ollama logs for any errors.
- **Model Not Found**: Make sure you've pulled the Llama3 model using `ollama pull llama3`.
- **Port Conflicts**: If port 8081 is in use, you can change it in `application.properties` by modifying `server.port`.
- **Java Version**: Verify you're using Java 21 by running `java -version`.
- **Build Errors**: Run `./mvnw clean` to clear any cached build artifacts and try again.

## Dependencies

The project uses the following main dependencies:

- Spring Boot Starter Web
- Spring AI Ollama Starter
- Spring AI Jsoup Document Reader
- Lombok (for reducing boilerplate code)

All dependencies are managed through Maven and will be downloaded automatically during the build process.

## Logging

The application uses SLF4J with Logback for logging. Debug logs for Spring AI are enabled by default. You can adjust logging levels in `application.properties` if needed.
