package dev.markconley.tinymaven.task;

import dev.markconley.tinymaven.exception.TinyMavenException;

public interface Task {
	void execute() throws TinyMavenException;
}
