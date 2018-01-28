package http;

public class EnptyRequestException extends Exception{

	private static final long serialVersionUID = 1L;

	public EnptyRequestException() {
		super();
	}

	public EnptyRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnptyRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnptyRequestException(String message) {
		super(message);
	}

	public EnptyRequestException(Throwable cause) {
		super(cause);
	}

}
