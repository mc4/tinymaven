package dev.markconley.tinymaven;

import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.task.PackageJarTask;
import dev.markconley.tinymaven.task.PackageWarTask;
import dev.markconley.tinymaven.task.SourceCompileTask;
import dev.markconley.tinymaven.task.Task;
import dev.markconley.tinymaven.task.TestTask;

public class TinyMaven {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];
        ProjectConfig projectConfig = ConfigLoader.loadConfig("build.yaml");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        Map<String, Task> taskMap = createTaskMap(compiler);

        Task selectedTask = taskMap.get(command);
        if (selectedTask != null) {
            selectedTask.execute(projectConfig);
        } else {
            System.out.println("Unknown command: " + command);
            printUsage();
        }
    }

    private static Map<String, Task> createTaskMap(JavaCompiler compiler) {
        Map<String, Task> taskMap = new HashMap<>();
        taskMap.put("build", new SourceCompileTask(compiler));
        taskMap.put("test", new TestTask(compiler));
        taskMap.put("packagejar", new PackageJarTask());
        taskMap.put("packagewar", new PackageWarTask());
        return taskMap;
    }

    private static void printUsage() {
        System.out.println("""
            TinyMaven - Minimal Java Build Tool
            Usage:
              java -jar tinymaven.jar build     	Compile source files
              java -jar tinymaven.jar test    		Run tests
              java -jar tinymaven.jar packagejar  	Create executable JAR
              java -jar tinymaven.jar packagewar 	Create WAR
            """);
    }
}
