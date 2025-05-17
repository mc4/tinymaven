package dev.markconley.tinymaven;

import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.task.CompileTask;
import dev.markconley.tinymaven.task.PackageTask;
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

		Map<String, Task> taskMap = Map.of(
			    "build", new CompileTask(compiler),
			    "test", new TestTask(compiler),
			    "package", new PackageTask()
			);

			Task selectedTask = taskMap.get(command);
			if (selectedTask != null) {
				selectedTask.execute(projectConfig);
			} else {
				System.out.println("Unknown command: " + command);
				printUsage();
				return;
			}

		}

		private static void printUsage() {
			System.out.println("""
					TinyMaven - Minimal Java Build Tool
					Usage:
					  java -jar tinymaven.jar build     Compile source files
					  java -jar tinymaven.jar test      Run tests
					  java -jar tinymaven.jar package   Create executable JAR
					""");
		}

}
