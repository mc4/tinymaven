package dev.markconley.tinymaven.exception;

public class DependencyResolutionException extends TinyMavenException {

	private static final long serialVersionUID = 5842795244266589579L;

	public DependencyResolutionException() {
		super();
	}

	public DependencyResolutionException(String message) {
		super(message);
	}

	public DependencyResolutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DependencyResolutionException(Throwable cause) {
		super(cause);
	}

}
