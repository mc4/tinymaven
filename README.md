# TinyMaven

**TinyMaven** is a minimalistic Java build tool designed to compile Java source code, run tests, and package your project into executable JAR or WAR files. It is inspired by Maven but lightweight and easy to customize.

---

## Features

- Compile Java source files using the system Java compiler  
- Run Java test classes  
- Package compiled classes into executable JAR files with customizable manifests  
- Package compiled classes and resources into WAR files for web applications  
- Simple, pluggable task system supporting build, test, packagejar, and packagewar commands  
- Configurable via a YAML file (`build.yaml`) for project metadata and paths  

---

## Getting Started

### Prerequisites

- JDK 11 or later (tested on JDK 24)  
- Git (to clone this repository)  

### Installation

Clone the repository and build the project:

```bash
git clone https://github.com/yourusername/tinymaven.git
cd tinymaven
./gradlew build  # or ./mvnw package if you prefer Maven
```
The compiled tinymaven.jar will be available in build/libs or target.

## Usage
Run TinyMaven with one of the following commands:
```bash
java -jar tinymaven.jar <command>
```

# Supported Commands
| Command      | Description                       |
| ------------ | --------------------------------- |
| `build`      | Compile source Java files         |
| `test`       | Run Java test classes             |
| `packagejar` | Package compiled classes into JAR |
| `packagewar` | Package compiled classes into WAR |

## Example
Compile sources and package a JAR:

```bash
java -jar tinymaven.jar build
java -jar tinymaven.jar packagejar
```

## Configuration
TinyMaven uses a build.yaml configuration file for project settings:
```yaml
project:
  name: MyProject
  mainClass: com.example.Main
  version: 1.0.0

sourceDirectory: src/main/java
testDirectory: src/test/java
outputDirectory: build/classes
```

project.name: Project artifact name
project.mainClass: Fully qualified main class name for manifest
sourceDirectory: Location of source Java files
testDirectory: Location of test Java files
outputDirectory: Location where compiled classes will be placed

## Architecture & Design
- Task-based architecture: Implements a Task interface to support build, test, package operations
- Dependency Injection: Shared dependencies (e.g. JavaCompiler) are injected into tasks
- Manifest Handling: Custom JAR manifest with project metadata
- File handling: Uses Java NIO for file walking and packaging
- Extensible: Easily add new tasks like WAR packaging or custom steps

## Acknowledgments
- Inspired by Apache Maven and Gradle
- Uses Java standard compiler API and NIO libraries
