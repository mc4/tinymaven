package dev.markconley.tinymaven.task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.markconley.tinymaven.config.ProjectConfig;

public class TaskManager {

	private final Map<String, Task> taskRegistry = new LinkedHashMap<>();

	public TaskManager registerTask(String name, Task task) {
		taskRegistry.put(name, task);
		return this;
	}

	public void runTasks(List<String> taskNames, ProjectConfig config) {
		for (String taskName : taskNames) {
			Task task = taskRegistry.get(taskName);
			if (task == null) {
				System.err.println("Unknown task: " + taskName);
				continue;
			}
			System.out.println("Starting task: " + taskName);
			try {
				task.execute(config);
				System.out.println("Completed task: " + taskName);
			} catch (Exception e) {
				System.err.println("Task '" + taskName + "' failed: " + e.getMessage());
				e.printStackTrace();
				break;
			}
		}
	}
	
}
