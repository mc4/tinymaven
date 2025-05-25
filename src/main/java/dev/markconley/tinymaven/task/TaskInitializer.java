package dev.markconley.tinymaven.task;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TaskInitializer {

    public static void initialize(TaskManager taskManager) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        taskManager.registerTask("clean", new CleanTask());
        taskManager.registerTask("compile", new SourceCompileTask(compiler));
        taskManager.registerTask("testcompile", new TestCompileTask(compiler));
        taskManager.registerTask("test", new TestRunnerTask());
        taskManager.registerTask("packagejar", new PackageJarTask());
        taskManager.registerTask("packagewar", new PackageWarTask());
        taskManager.registerTask("build", new BuildTask(taskManager));
    }
}
