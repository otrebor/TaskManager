package org.exercise.taskmanager.service.impl;

import lombok.NonNull;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.model.taskmanager.ProcessDescriptor;
import org.exercise.taskmanager.model.taskmanager.ProcessOrdering;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

public class PriorityBasedTaskManagerService extends TaskManagerServiceBase {

    private final PriorityBlockingQueue<ProcessDescriptor> priorityQueue;

    protected PriorityBasedTaskManagerService(int maxNumberAllowedProcesses) {
        super(maxNumberAllowedProcesses, ProcessOrdering.PRIORITY_AND_TIME);
        priorityQueue = new PriorityBlockingQueue<>(
                maxNumberAllowedProcesses,
                ProcessOrdering.PRIORITY_AND_TIME.getComparingStrategy().reversed());
    }

    @Override
    public synchronized void addProcess(Process processToAdd) throws AcceptProcessException {
        checkIfAlreadyActive(processToAdd);

        if (priorityQueue.size() == maxNumberAllowedProcesses) {
            try {
                ProcessDescriptor out = priorityQueue.take();
                if (out.getProcess().getPriority().getValue().compareTo(processToAdd.getPriority().getValue()) > 0) {
                    kill(out.getProcess());
                } else {
                    return;
                }
            } catch (Exception e) {
                throw new AcceptProcessException("Error while killing process", e);
            }
        }

        super.addProcess(processToAdd);
        priorityQueue.add(activeProcessesMap.get(processToAdd.getPid()));
    }

    @Override
    public synchronized void kill(@NonNull Process processToKill) throws KillProcessException, ProcessNotFoundException {
        ProcessDescriptor descriptor = Optional.ofNullable(activeProcessesMap.get(processToKill.getPid()))
                .orElseThrow(() -> new ProcessNotFoundException(String.format("process with PID %s not found", processToKill.getPid())));

        super.kill(processToKill);

        priorityQueue.remove(descriptor);
    }

    @Override
    public synchronized void killGroup(@NonNull Priority priorityGroup) throws KillProcessException {
        List<ProcessDescriptor> processesInGroup = activeProcessesMap.values().stream()
                .filter(p -> priorityGroup.equals(p.getProcess().getPriority()))
                .collect(Collectors.toList());

        super.killGroup(priorityGroup);
        priorityQueue.removeAll(processesInGroup);
    }

    @Override
    public synchronized void killAll() throws KillProcessException {
        super.killAll();
        priorityQueue.clear();
    }
}
