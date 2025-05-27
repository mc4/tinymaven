package dev.markconley.tinymaven.exception;


public class TinyMavenException extends Exception {
	
    private static final long serialVersionUID = -2394197328916268922L;

	public TinyMavenException() {
        super();
    }

    public TinyMavenException(String message) {
        super(message);
    }

    public TinyMavenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TinyMavenException(Throwable cause) {
        super(cause);
    }
}

