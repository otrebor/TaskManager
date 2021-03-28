package org.exercise.taskmanager.model.taskmanager;

import lombok.Getter;
import lombok.NonNull;
import org.exercise.taskmanager.model.process.Process;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProcessDescriptor {

    @Getter
    private final Process process;

    @Getter
    private final LocalDateTime localDateTime;

    public ProcessDescriptor(@NonNull Process process, @NonNull LocalDateTime localDateTime) {
        this.process = process;
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessDescriptor that = (ProcessDescriptor) o;
        return process.equals(that.process);
    }

    @Override
    public int hashCode() {
        return Objects.hash(process);
    }

    @Override
    public String toString() {
        return "ProcessDescriptor{" +
                "process=" + process +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
