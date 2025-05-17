package dev.markconley.tinymaven.task;

import dev.markconley.tinymaven.config.ProjectConfig;

public interface Task {
	void execute(ProjectConfig config);
}
