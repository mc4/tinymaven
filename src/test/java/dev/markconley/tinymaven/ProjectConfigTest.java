package dev.markconley.tinymaven;

import dev.markconley.tinymaven.annotation.TinyTest;
import dev.markconley.tinymaven.config.ProjectConfig;

public class ProjectConfigTest {
	
	private final static String DEFAULT_MAIN_SOURCE_DIR = "src/main/java";
	private final static String DEFAULT_OUTPUT_DIR = "build/classes";

	@TinyTest
	public void testShouldReturnDefaultSourceDirectory() {
		ProjectConfig config = new ProjectConfig();
		if (!DEFAULT_MAIN_SOURCE_DIR.equals(config.getSourceDirectory())) {
			throw new AssertionError("Expected default source directory to be 'src/main/java'");
		}
	}

	@TinyTest
	public void testShouldReturnDefaultOutputDirectory() {
		ProjectConfig config = new ProjectConfig();
		if (!DEFAULT_OUTPUT_DIR.equals(config.getOutputDirectory())) {
			throw new AssertionError("Expected default output directory to be 'build/classes'");
		}
	}
	
}
