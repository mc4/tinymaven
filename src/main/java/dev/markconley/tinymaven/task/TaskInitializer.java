package dev.markconley.tinymaven.task;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import dev.markconley.tinymaven.config.ProjectConfig;

public class TaskInitializer {
	public static void initialize(TaskManager taskManager, ProjectConfig config) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		taskManager.registerTask("clean", new CleanTask(config));
		taskManager.registerTask("sourceCompile", new SourceCompileTask(config, compiler));
		taskManager.registerTask("testCompile", new TestCompileTask(config, compiler));
		taskManager.registerTask("testRun", new TestRunnerTask(config));
		taskManager.registerTask("packageJar", new PackageJarTask(config));
		taskManager.registerTask("packageWar", new PackageWarTask(config));
	}
}
