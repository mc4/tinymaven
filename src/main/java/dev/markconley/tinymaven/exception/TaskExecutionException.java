package dev.markconley.tinymaven.exception;

public class TaskExecutionException extends TinyMavenException {
	
	private static final long serialVersionUID = -8326128787452274519L;

	public TaskExecutionException() {
		super();
	}

	public TaskExecutionException(String message) {
		super(message);
	}

	public TaskExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskExecutionException(Throwable cause) {
		super(cause);
	}
}