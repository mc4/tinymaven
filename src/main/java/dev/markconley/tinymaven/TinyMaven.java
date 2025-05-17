package dev.markconley.tinymaven;

import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class TinyMaven {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsage();
			return;
		}

		String command = args[0];
		ProjectConfig config = loadConfig("build.yaml");

		switch (command) {
			case "build" -> compileSources(config);
			case "test" -> runTests(config);
			case "package" -> packageJar(config);
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

	private static ProjectConfig loadConfig(String path) {
		try (InputStream inputStream = new FileInputStream(path)) {
			Yaml yaml = new Yaml(new Constructor(ProjectConfig.class, new LoaderOptions()));
			return yaml.load(inputStream);
		} catch (Exception e) {
			System.err.println("Failed to load build.yaml: " + e.getMessage());
			System.exit(1);
			return null; // return statement is required by compiler
		}
	}

	private static void compileSources(ProjectConfig config) {
		System.out.println("Compiling sources...");
		// TODO: Use JavaCompiler API to compile src/**/*.java into build/classes
	}

	private static void runTests(ProjectConfig config) {
		System.out.println("Running tests...");
		// TODO: Scan for test classes, run methods, report results
	}

	private static void packageJar(ProjectConfig config) {
		System.out.println("Packaging JAR...");
		// TODO: Bundle compiled classes into a JAR with manifest
	}
}
