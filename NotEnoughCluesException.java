package com.goldman;

public class NotEnoughCluesException extends Exception {

	private static final long serialVersionUID = -2901663482155879666L;

	public NotEnoughCluesException(String message) {
        super(message);
    }

}