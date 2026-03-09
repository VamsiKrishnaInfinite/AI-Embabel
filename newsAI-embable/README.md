# NewsAI-Embabel

A Spring Boot application that uses AI to summarize and compare news articles via REST endpoints.

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java 17**: Download and install from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://openjdk.java.net/projects/jdk/17/).
- **Apache Maven**: Download from [Maven official site](https://maven.apache.org/download.cgi). Ensure `mvn` is in your PATH.
- **Ollama**: Download and install from [Ollama website](https://ollama.com/). This is required for the AI functionality.

## Installation and Setup

1. **Clone or Download the Project**:
   - Navigate to the project directory: `C:\Users\vamsiko\workspace\newsAI-embable\newsAI-embabel`

2. **Install Dependencies**:
   - Open a terminal (PowerShell) in the project root directory (`newsAI-embabel`).
   - Run the following command to download dependencies:
     ```
     mvn clean install
     ```
   - This will compile the project and download all required JARs.

3. **Set Up Ollama**:
   - Start the Ollama service (if not already running).
   - Pull the required model:
     ```
     ollama pull llama3:latest
     ```
   - Ensure Ollama is running on `http://localhost:11434` (default port).

## Running the Application

1. **Start the Spring Boot Application**:
   - In the terminal, from the `newsAI-embabel` directory, run:
     ```
     mvn spring-boot:run
     ```
   - The application will start on port 8080. You should see output indicating the server is running.

2. **Verify the Application is Running**:
   - Open a web browser and go to `http://localhost:8080`.
   - The application should respond (though no root endpoint is defined, you can test the API endpoints below).

## API Endpoints

The application provides the following REST endpoints:

- **Summarize a News Article**:
  - URL: `http://localhost:8080/news/summarize?file=<filename>`
  - Method: GET
  - Example: `http://localhost:8080/news/summarize?file=uber.html`
  - This endpoint summarizes the content of the specified file (e.g., `uber.html` or `uber2.html` in the resources folder).

- **Compare Two News Articles**:
  - URL: `http://localhost:8080/news/compare?file1=<filename1>&file2=<filename2>`
  - Method: GET
  - Example: `http://localhost:8080/news/compare?file1=uber.html&file2=uber2.html`
  - This endpoint compares the content of two specified files.

Note: The files (`uber.html`, `uber2.html`) are located in the `src/main/resources` directory and are used for testing purposes.

## Stopping the Application

- In the terminal where the application is running, press `Ctrl + C` to stop the server.

## Troubleshooting

- **Port Conflict**: If port 8080 is in use, change it in `src/main/resources/application.properties` by modifying `server.port`.
- **Ollama Not Running**: Ensure Ollama is started and the model is pulled. Check `http://localhost:11434` in a browser.
- **Java Version**: Verify Java 17 is installed with `java -version`.
- **Maven Issues**: Ensure Maven is installed and `mvn` command works.

## Project Structure

- `src/main/java`: Java source code
  - `com/infinite/newsAIEmbael/`: Main package
    - `NewsAiEmbabelApplication.java`: Main Spring Boot class
    - `controller/`: REST controllers
    - `agent/`: Business logic for news processing
    - `config/`: Configuration classes
    - `dto/`: Data transfer objects
    - `tools/`: Utility tools
- `src/main/resources`: Configuration and test files
- `pom.xml`: Maven configuration

## Dependencies

- Spring Boot 4.0.3
- Spring AI Ollama
- Embabel Agent Starter
- Jackson for JSON processing
