package dev.markconley.tinymaven.task;

import java.util.List;
import java.util.Objects;

import dev.markconley.tinymaven.config.BuildLifeCycle;
import dev.markconley.tinymaven.config.BuildStep;
import dev.markconley.tinymaven.config.LifeCycleConfig;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class BuildTask implements Task {

    private final TaskManager taskManager;
    private final ProjectConfig config;

    public BuildTask(TaskManager taskManager, ProjectConfig config) {
        this.taskManager = Objects.requireNonNull(taskManager, "TaskManager cannot be null");
        this.config = Objects.requireNonNull(config, "Project Configuration cannot be null");
    }

    @Override
    public void execute() throws TinyMavenException {
        List<BuildStep> lifeCycleSteps = LifeCycleConfig.getLifeCycleSteps(config.getProject().getPackaging(), BuildLifeCycle.BUILD);

        System.out.println("Starting build lifecycle with steps: " + lifeCycleSteps);

        for (BuildStep step : lifeCycleSteps) {
            Task task = taskManager.getTask(step.name());
            if (task == null) {
                System.err.println("No task registered for step '" + step.name() + "'. Skipping.");
                continue;
            }

            System.out.println("Executing step: " + step.name());
            try {
                task.execute();
            } catch (Exception e) {
                System.err.println("Error executing step '" + step.name() + "': " + e.getMessage());
                e.printStackTrace();
                System.out.println("Build failed.");
                return;
            }
        }

        System.out.println("Build lifecycle completed successfully.");
    }
    
}

