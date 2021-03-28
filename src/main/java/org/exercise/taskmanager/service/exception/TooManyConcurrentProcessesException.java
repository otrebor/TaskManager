package org.exercise.taskmanager.service.exception;

public class TooManyConcurrentProcessesException extends AcceptProcessException {

    public TooManyConcurrentProcessesException(String message) {
        super(message);
    }

    public TooManyConcurrentProcessesException(String message, Throwable cause) {
        super(message, cause);
    }
}
