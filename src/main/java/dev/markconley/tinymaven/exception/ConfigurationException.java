package dev.markconley.tinymaven.exception;

public class ConfigurationException extends TinyMavenException {

	private static final long serialVersionUID = 5769144771866787251L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
