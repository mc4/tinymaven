package dev.markconley.tinymaven.task;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.runner.TinyTestRunner;

import java.nio.file.Path;

public class TestRunnerTask implements Task {

	private final TinyTestRunner runner;

	public TestRunnerTask() {
		this.runner = new TinyTestRunner();
	}

	@Override
	public void execute(ProjectConfig config) {
		Path testClassesDir = Path.of(config.getTestOutputDirectory());
		try {
			runner.runTests(testClassesDir);
		} catch (Exception e) {
			System.err.println("Error running tests:");
			e.printStackTrace();
		}
	}
}
