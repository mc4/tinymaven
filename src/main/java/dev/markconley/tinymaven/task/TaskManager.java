package dev.markconley.tinymaven.task;

import java.util.HashMap;
import java.util.Map;

import dev.markconley.tinymaven.config.LifeCycleConfig;
import dev.markconley.tinymaven.config.ProjectConfig;

public class TaskManager {

    private final Map<String, Task> tasks = new HashMap<>();

    public void registerTask(String name, Task task) {
        tasks.put(name.toLowerCase(), task);
    }

    public Task getTask(String name) {
        return tasks.get(name.toLowerCase());
    }

    public void executeTask(String name, ProjectConfig config) {
        Task task = getTask(name);
        if (task == null) {
            System.err.println("Unknown task: " + name);
            return;
        }
        task.execute(config);
    }

    public void executeLifecycle(String lifeCycleName, ProjectConfig config) {
        for (String step : LifeCycleConfig.getLifeCycleSteps(config.getProject().getPackaging(), lifeCycleName)) {
            try {
                System.out.println("Executing task: " + step);
                executeTask(step, config);
            } catch (Exception e) {
                System.err.println("Task '" + step + "' failed. Aborting program.");
                break; 
            }
        }
    }

}

