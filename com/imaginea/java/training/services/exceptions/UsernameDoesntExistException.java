package com.imaginea.java.training.services.exceptions;

public class UsernameDoesntExistException extends RuntimeException {
	private static final long serialVersionUID = 8948303014588489880L;

	public UsernameDoesntExistException() {
		super();

	}

	public UsernameDoesntExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public UsernameDoesntExistException(String message, Throwable cause) {
		super(message, cause);

	}

	public UsernameDoesntExistException(String message) {
		super(message);

	}

	public UsernameDoesntExistException(Throwable cause) {
		super(cause);

	}

}
