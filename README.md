# Selenide Project

This project is built using Maven and designed to run automated UI tests using [Selenide](https://selenide.org/). It
includes dependencies for logging, reporting, and test execution with TestNG, Allure, and SLF4J. The project uses Java
11 and integrates tools like Checkstyle for code quality checks.

## Requirements

- **Java 11**: Ensure that you have JDK 11 installed.
- **Maven**: This project uses Maven for dependency management and build automation.

## Project Structure

- **Group ID**: `com.agest`
- **Artifact ID**: `selenide-project`
- **Version**: `1.0-SNAPSHOT`

## Dependencies

- **Selenide** (`5.25.0`): Framework for UI testing.
- **Lombok** (`1.18.34`): Simplifies Java code by generating boilerplate code such as getters, setters, etc.
- **SLF4J** (`1.7.30`): Simple Logging Facade for Java.
- **Allure TestNG** (`2.15.0`): For generating detailed test reports.
- **TestNG** (`7.7.1`): Testing framework.
- **WebDriverManager** (`5.9.2`): Manages WebDriver binaries automatically.
- **HttpClient5** (`5.2.1`): Apache HTTP client for making HTTP requests.
- **Commons Lang** (`3.12.0`): Utilities for Java's core libraries.

## Plugins

### Build Plugins

- **Maven Surefire Plugin** (`3.0.0-M7`): Runs unit tests using TestNG.
    - **AspectJ Weaver** (`1.9.19`): Java agent used for weaving aspects at runtime.

- **Maven Checkstyle Plugin** (`3.5.0`): Enforces coding standards with Checkstyle.
    - Configured to fail the build on coding violations.
    - Uses the configuration file `checkstyle.xml`.

### Reporting Plugins

- **Maven Site Plugin** (`3.7.1`): Generates a project website.
- **Maven Project Info Reports Plugin** (`3.0.0`): Provides detailed project reports.
- **Checkstyle Report**: Generates a Checkstyle report for code quality analysis.

## Setup, Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/selenide-project.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd selenide-project
    ```

3. **Build the project**:
    ```bash
    mvn clean install
    ```

## Install TA to run test with TA Dashboard

Please contact with Agest to get TA installation. <br>
Once you have TA installed, please import Test repo to Repository Server using Test.dat file
from`src/test/resources/test_data/Test_2024-09-18_14-22-25.dat`

## Run Tests

1. **To run all test:**
   ```bash
      mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/test.xml
   ```
2. **To run specific test you can select the test to run in specific.test.xml then run this command:**
   ```bash
     mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/specific.test.xml
   ```
3. **To run selenium grid you can config the remote url in Constants.GRID_HUB_URL then enable the remote flag using:**
   ```bash
     -Dremote=true
   ```
4. **To run parallel enter number of thread-count using:**
   ```bash
     -DthreadCount={number-of-thread-count} 
      example: -DthreadCount=5
   ```
5. **To run test with specific browser (default is chrome):**
    ```bash
      -Dselenide.browser={browser}
      example: -Dselenide.browser=edge
    ```

5. **Example of full maven command with all option**
    ```bash
      mvn -Dselenide.browser=edge clean test -Dsurefire.suiteXmlFiles=src/test/resources/grid.test.xml -Dremote=true -DthreadCount=4
    ```

## How to get report

- **Generate Allure Report**:
  <br>
  Make sure you delete folder allure-results before run test.
  <br>
  After running the tests, generate the Allure report by running:

```bash
allure serve
```

Or you can get html report after generate single-file report by running:

```bash
allure generate --single-file
```

Now you can get html report by open <b>index.html</b> in allure-report folder

## Maven Other Commands

- **Generate project site**:
    ```bash
    mvn site
    ```

- **Check for code style issues**:
    ```bash
    mvn checkstyle:check
    ```

