package org.exercise.taskmanager.service.exception;

public class ProcessAlreadyActiveException extends AcceptProcessException {

    public ProcessAlreadyActiveException(String message) {
        super(message);
    }

    public ProcessAlreadyActiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
