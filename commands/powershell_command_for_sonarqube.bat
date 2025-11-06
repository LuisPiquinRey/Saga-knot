@echo off
echo 🧠 Running SonarCloud analysis...

if "%SONAR_TOKEN%"=="" (
    echo ERROR: SONAR_TOKEN environment variable is not set.
    pause
    exit /b 1
)

mvn clean verify sonar:sonar ^
  -DskipTests=true ^
  -Dsonar.projectKey=LuisPiquinRey_Saga-knot ^
  -Dsonar.organization=luispiquinrey ^
  -Dsonar.host.url=https://sonarcloud.io ^
  -Dsonar.login=%SONAR_TOKEN%

pause
