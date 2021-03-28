package org.exercise.taskmanager.model.taskmanager;

import java.util.Comparator;

public enum ProcessOrdering {
    ACTIVATION_TIME(Comparator.comparing(ProcessDescriptor::getLocalDateTime)
            .thenComparing((ProcessDescriptor descriptor) -> descriptor.getProcess().getPid())),

    FIFO(Comparator.comparing(ProcessDescriptor::getLocalDateTime).reversed()
            .thenComparing((ProcessDescriptor descriptor) -> descriptor.getProcess().getPid())),

    PRIORITY_AND_TIME(Comparator.comparing((ProcessDescriptor descriptor) -> descriptor.getProcess().getPriority().getValue())
            .thenComparing(ProcessDescriptor::getLocalDateTime)
            .thenComparing((ProcessDescriptor descriptor) -> descriptor.getProcess().getPid()));

    private final Comparator<? super ProcessDescriptor> comparingStrategy;

    ProcessOrdering(Comparator<? super ProcessDescriptor> comparingStrategy) {
        this.comparingStrategy = comparingStrategy;
    }

    public Comparator<? super ProcessDescriptor> getComparingStrategy() {
        return comparingStrategy;
    }
}
