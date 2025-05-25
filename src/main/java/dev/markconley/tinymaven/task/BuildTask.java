package dev.markconley.tinymaven.task;

import java.util.List;
import java.util.Objects;

import dev.markconley.tinymaven.config.LifeCycleConfig;
import dev.markconley.tinymaven.config.ProjectConfig;

public class BuildTask implements Task {

    private final TaskManager taskManager;

    public BuildTask(TaskManager taskManager) {
        this.taskManager = Objects.requireNonNull(taskManager, "TaskManager cannot be null");
    }

    @Override
    public void execute(ProjectConfig config) {
        List<String> lifeCycleSteps = LifeCycleConfig.getLifeCycleSteps(config.getProject().getPackaging(), "build");

        System.out.println("Starting build lifecycle with steps: " + lifeCycleSteps);

        for (String stepName : lifeCycleSteps) {
            Task task = taskManager.getTask(stepName);
            if (task == null) {
                System.err.println("No task registered for step '" + stepName + "'. Skipping.");
                continue;
            }

            System.out.println("Executing step: " + stepName);
            try {
                task.execute(config);
            } catch (Exception e) {
                System.err.println("Error executing step '" + stepName + "': " + e.getMessage());
                e.printStackTrace();
                System.out.println("Build failed.");
                return;
            }
        }

        System.out.println("Build lifecycle completed successfully.");
    }
    
}

