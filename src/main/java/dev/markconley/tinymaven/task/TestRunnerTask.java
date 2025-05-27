package dev.markconley.tinymaven.task;

import java.nio.file.Path;
import java.util.Objects;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;
import dev.markconley.tinymaven.runner.TinyTestRunner;

public class TestRunnerTask implements Task {

	private final TinyTestRunner runner;
	private final ProjectConfig config;

	public TestRunnerTask(ProjectConfig config) {
		this.runner = new TinyTestRunner();
		this.config = Objects.requireNonNull(config, "Project Configuration cannot be null");
	}

	@Override
	public void execute() throws TinyMavenException {
		Path testClassesDir = Path.of(config.getTestOutputDirectory());
		try {
			runner.runTests(testClassesDir);
		} catch (Exception e) {
			System.err.println("Error running tests:");
			e.printStackTrace();
		}
	}

}
