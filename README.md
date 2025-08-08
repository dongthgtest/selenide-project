# Selenide Project

This project is built using Maven and designed to run automated UI tests using [Selenide](https://selenide.org/). It
includes dependencies for logging, reporting, and test execution with TestNG, Allure, and SLF4J. The project uses Java
21 and integrates tools like Checkstyle for code quality checks.

### Outcomes

- [x] Selenide FW ([selenide.org](https://selenide.org/)): automation/test framework
- [x] Reports: HTML, Allure Report, Report Portal
- [x] Test retry: test failed ⇒ retry (1, 2)
- [x] Parallel/distributed testing
- [x] Cross browsers testing: Chrome, Edge
- [x] Selenium Grid/Shard
- [ ] Test cases: VJ, Agoda, TBD (5/8 - 3 Agoda tests, 2 VJ tests)
- [ ] CI: Schedule test, send email notification result with summary

### Use Cases

- [ ] Content testing
- [x] Multiple languages testing
- [x] Group tests by purposes: regression, smoke/sanity test
- [x] Source control practice: branch
- [ ] Switch test environment: dev, stg (dev: agoda.com, stg: vj.com)
- [x] Wrap custom controls
- [ ] Data driven testing: test data is in excel file
- [ ] Working with Shadow DOM
- [ ] Compare with another FW e.g. Playwright

---

## Requirements

- **Java 21**: Ensure that you have JDK 21+ installed.
- **Maven**: This project uses Maven for dependency management and build automation.

## Project Structure

- **Group ID**: `com.agest`
- **Artifact ID**: `selenide-project`
- **Version**: `1.0-SNAPSHOT`

## Dependencies

- **Selenide:** 7.9.3
- **TestNG:** 7.10.2
- **Allure TestNG:** 2.15.0
- **Gson:** 2.8.9
- **Lombok:** 1.18.34
- **SLF4J:** 1.7.30
- **AspectJ Weaver:** 1.9.19
- **Apache Commons Lang:** 3.12.0

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

## Run Tests

1. **To run all test:**
   ```bash
      mvn clean test
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
