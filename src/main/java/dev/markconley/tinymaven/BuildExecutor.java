package dev.markconley.tinymaven;

import dev.markconley.tinymaven.config.ProjectConfig;

public class BuildExecutor {
	private final ProjectConfig config;

	public BuildExecutor(ProjectConfig config) {
		this.config = config;
	}

	public void compileSources() {
		System.out.println("Compiling sources...");
		// TODO: Use JavaCompiler API to compile src/**/*.java into build/classes
	}

	public void runTests() {
		System.out.println("Running tests...");
		// TODO: Scan for test classes, run methods, report results
	}

	public void packageJar() {
		System.out.println("Packaging JAR...");
		// TODO: Bundle compiled classes into a JAR with manifest!!!
	}
}
