package dev.markconley.tinymaven;

import dev.markconley.tinymaven.config.ProjectConfig;

public class TinyMaven {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsage();
			return;
		}

		String command = args[0];
		ProjectConfig projectConfig = ConfigLoader.loadConfig("build.yaml");

		BuildExecutor buildExecutor = new BuildExecutor(projectConfig);

		switch (command) {
			case "build" -> buildExecutor.compileSources();
			case "test" -> buildExecutor.runTests();
			case "package" -> buildExecutor.packageJar();
			default -> {
				System.out.println("Unknown command: " + command);
				printUsage();
			}
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
