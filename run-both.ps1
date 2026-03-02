param(
    [int]$NewsAiPort = 8081,
    [int]$EmbabelPort = 8080
)

# Build both modules
Write-Host "Building newsAI..."
cd .\newsAI
.\mvnw.cmd clean package -DskipTests

Write-Host "Building newsAI-embabel..."
cd ..\newsAI-embabel
mvn clean package -DskipTests

# Start newsAI on explicit port
Write-Host "Starting newsAI on port $NewsAiPort..."
$newsJar = Get-ChildItem -Path "..\newsAI\target" -Filter "newsAI-*.jar" | Select-Object -First 1
if (-not $newsJar) { Write-Error "newsAI jar not found. Build may have failed."; exit 1 }
$newsProc = Start-Process -FilePath "java" -ArgumentList "-jar`,""$($newsJar.FullName)`"`
