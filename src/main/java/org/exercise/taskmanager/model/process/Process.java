package org.exercise.taskmanager.model.process;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class Process {

    @Getter
    private final UUID pid;

    @Getter
    private final Priority priority;

    public Process(@NonNull UUID pid, @NonNull Priority priority) {
        this.pid = pid;
        this.priority = priority;
    }

    public boolean acceptSignal(Signal signal) {
        // shutdown process
        return true;
    }

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", priority=" + priority +
                '}';
    }
}
