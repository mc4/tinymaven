package dev.markconley.tinymaven;

import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import dev.markconley.tinymaven.config.ConfigLoader;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.task.PackageJarTask;
import dev.markconley.tinymaven.task.PackageWarTask;
import dev.markconley.tinymaven.task.SourceCompileTask;
import dev.markconley.tinymaven.task.Task;
import dev.markconley.tinymaven.task.TestCompileTask;

public class TinyMaven {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsage();
			return;
		}

		String command = args[0];
		ProjectConfig projectConfig = ConfigLoader.loadConfig("build.yaml");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		Task packagingTask = resolvePackagingTask(projectConfig);

		Map<String, Task> taskMap = Map.of(
				"build", new SourceCompileTask(compiler), 
				"test", new TestCompileTask(compiler), 
				"package", packagingTask);

		Task selectedTask = taskMap.get(command);
		if (selectedTask != null) {
			selectedTask.execute(projectConfig);
		} else {
			System.out.println("Unknown command: " + command);
			printUsage();
		}
	}

	private static Task resolvePackagingTask(ProjectConfig config) {
		String packaging = config.getProject().getPackaging().toLowerCase();
		return switch (packaging) {
			case "war" -> new PackageWarTask();
			case "jar" -> new PackageJarTask();
			default -> {
				System.out.println("Unsupported packaging type: " + packaging + ". Defaulting to jar.");
				yield new PackageJarTask();
			}
		};
	}

	private static void printUsage() {
		System.out.println("""
				TinyMaven - Minimal Java Build Tool
				Usage:
				  java -jar tinymaven.jar build      Compile source files
				  java -jar tinymaven.jar test       Run tests
				  java -jar tinymaven.jar package    Create JAR or WAR based on build.yaml
				""");
	}
}
