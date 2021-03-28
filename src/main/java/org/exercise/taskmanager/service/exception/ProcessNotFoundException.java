package org.exercise.taskmanager.service.exception;

public class ProcessNotFoundException extends Exception {
    public ProcessNotFoundException(String message) {
        super(message);
    }
}
