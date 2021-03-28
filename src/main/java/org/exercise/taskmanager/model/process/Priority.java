package org.exercise.taskmanager.model.process;

public enum Priority implements Comparable<Priority> {
    LOW(100),
    MEDIUM(10),
    HIGH(1);

    private final Integer value;

    Priority(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
