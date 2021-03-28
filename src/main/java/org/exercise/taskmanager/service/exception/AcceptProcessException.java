package org.exercise.taskmanager.service.exception;

public class AcceptProcessException extends Exception {

    public AcceptProcessException(String message) {
        super(message);
    }

    public AcceptProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
