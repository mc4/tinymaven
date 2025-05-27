package dev.markconley.tinymaven.task;

import java.util.HashMap;
import java.util.Map;

import dev.markconley.tinymaven.config.BuildLifeCycle;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TaskExecutionException;

public class TaskManager {

    private final Map<String, Task> taskRegistry = new HashMap<>();
    private final ProjectConfig config;
    
    public TaskManager(ProjectConfig config) {
        this.config = config;
    }
    
    public void registerTask(String name, Task task) {
    	taskRegistry.put(name.toLowerCase(), task);
    }

    public Task getTask(String name) {
        return taskRegistry.get(name.toLowerCase());
    }

    public void executeTask(String name) throws Exception {
        Task task = taskRegistry.get(name.toLowerCase());
        if (task == null) {
            throw new TaskExecutionException("Task not found: " + name);
        }
        task.execute();
    }
    
    public void executeLifeCycle(BuildLifeCycle lifecycle) throws Exception {
        for (String taskName : BuildLifeCycle.getStepsFor(config.getProject().getPackaging(), lifecycle)) {
            executeTask(taskName);
        }
    }
    
    public boolean hasTask(String name) {
        return taskRegistry.containsKey(name.toLowerCase());
    }

}

