@echo off
:: Navigate to the directory where the Selenium Server JAR file is located
cd /d C:\Selenium\

:: Start the Selenium Hub
start cmd /k "java -jar selenium-server-4.25.0.jar hub"

:: Wait for 2 seconds to let the Hub start
timeout /t 2 /nobreak > NUL

:: Start the first Selenium Node (Chrome)
start cmd /k "java -jar selenium-server-4.25.0.jar node --port 5555 --hub http://localhost:4444"

:: Start the second Selenium Node (Edge)
start cmd /k "java -jar selenium-server-4.25.0.jar node --port 5556 --hub http://localhost:4444"

exit
