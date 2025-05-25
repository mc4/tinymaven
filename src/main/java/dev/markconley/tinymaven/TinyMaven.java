package dev.markconley.tinymaven;

import dev.markconley.tinymaven.config.ConfigLoader;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.task.Task;
import dev.markconley.tinymaven.task.TaskInitializer;
import dev.markconley.tinymaven.task.TaskManager;

public class TinyMaven {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];
        ProjectConfig projectConfig = ConfigLoader.loadConfig("build.yaml");

        TaskManager taskManager = new TaskManager();
        TaskInitializer.initialize(taskManager);

        Task selectedTask = taskManager.getTask(command);
        if (selectedTask != null) {
            selectedTask.execute(projectConfig);
        } else {
            System.out.println("Unknown command: " + command);
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("""
            TinyMaven - Minimal Java Build Tool
            Usage:
              java -jar tinymaven.jar build         Compile source files and run lifecycle
              java -jar tinymaven.jar clean         Clean build directories
              java -jar tinymaven.jar compile       Compile source code
              java -jar tinymaven.jar testcompile   Compile test code
              java -jar tinymaven.jar test          Run tests
              java -jar tinymaven.jar packagejar    Package as JAR
              java -jar tinymaven.jar packagewar    Package as WAR
            """);
    }
}

