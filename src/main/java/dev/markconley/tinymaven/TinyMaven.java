package dev.markconley.tinymaven;

import java.util.Arrays;

import dev.markconley.tinymaven.config.BuildLifeCycle;
import dev.markconley.tinymaven.config.ConfigLoader;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.ConfigurationException;
import dev.markconley.tinymaven.exception.DependencyResolutionException;
import dev.markconley.tinymaven.exception.TaskExecutionException;
import dev.markconley.tinymaven.exception.TinyMavenException;
import dev.markconley.tinymaven.task.TaskInitializer;
import dev.markconley.tinymaven.task.TaskManager;

public class TinyMaven {

	public static void main(String[] args) {

		try {
			if (args.length == 0) {
				System.out.println("Usage: tinymaven [build | test | compile | task1 task2 ...]");
				printUsage();
				System.exit(1);
			}

			ProjectConfig config = ConfigLoader.loadConfig("build.yaml");

			try {
				config = ConfigLoader.loadConfig("build.yaml");
			} catch (ConfigurationException e) {
				System.err.println("Failed to load project configuration: " + e.getMessage());
				System.exit(1);
			}

			TaskManager taskManager = new TaskManager(config);
			TaskInitializer.initialize(taskManager, config);

			boolean containsLifecycle = Arrays.stream(args).anyMatch(arg -> BuildLifeCycle.fromName(arg) != null);
			boolean containsTask = Arrays.stream(args).anyMatch(arg -> taskManager.hasTask(arg));

			if (containsLifecycle && containsTask) {
				System.out.println(
						"Error: Cannot mix lifecycle names (e.g. 'build') and individual tasks (e.g. 'clean') in the same command.");
				System.out.println("Please run them separately.");
				System.exit(1);
			}

			for (String arg : args) {
				try {
					BuildLifeCycle lifeCycle = BuildLifeCycle.fromName(arg);
					if (lifeCycle != null) {
						taskManager.executeLifeCycle(lifeCycle);
					} else if (taskManager.hasTask(arg)) {
						taskManager.executeTask(arg);
					} else {
						System.out.println("Unknown command or task: " + arg);
					}
				} catch (DependencyResolutionException e) {
					System.err.println("Dependency resolution error: " + e.getMessage());
					System.exit(1);
				} catch (TaskExecutionException e) {
					System.err.println("Task execution failed: " + e.getMessage());
					System.exit(1);
				} catch (TinyMavenException e) {
					System.err.println("Unexpected TinyMaven error: " + e.getMessage());
					System.exit(1);
				} catch (Exception e) {
					System.err.println("Unexpected error: " + e.getMessage());
					System.exit(1);
				}
			}
		} catch (Exception e) {
			System.err.println("Fatal error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void printUsage() {
		System.out.println("""
				TinyMaven - Minimal Java Build Tool
				Usage:
				  java -jar tinymaven.jar build         Run full build lifecycle (clean, compile, test, package)
				  java -jar tinymaven.jar clean         Clean build and output directories
				  java -jar tinymaven.jar compile       Compile main source code
				  java -jar tinymaven.jar testcompile   Compile test source code
				  java -jar tinymaven.jar test          Run tests
				  java -jar tinymaven.jar packagejar    Package compiled code as JAR
				  java -jar tinymaven.jar packagewar    Package compiled code as WAR
				""");
	}
	
}
