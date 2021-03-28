package org.exercise.taskmanager.service.impl;

import lombok.NonNull;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.model.process.Signal;
import org.exercise.taskmanager.model.taskmanager.ProcessDescriptor;
import org.exercise.taskmanager.model.taskmanager.ProcessOrdering;
import org.exercise.taskmanager.service.TaskManagerService;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessAlreadyActiveException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class TaskManagerServiceBase implements TaskManagerService {

    protected final int maxNumberAllowedProcesses;

    private final ProcessOrdering defaultProcessOrdering;

    protected final Map<UUID, ProcessDescriptor> activeProcessesMap;

    protected TaskManagerServiceBase(int maxNumberAllowedProcesses, @NonNull ProcessOrdering defaultOrdering) {
        this.maxNumberAllowedProcesses = maxNumberAllowedProcesses;
        this.defaultProcessOrdering = defaultOrdering;
        this.activeProcessesMap = new HashMap<>();
    }

    @Override
    public synchronized void addProcess(@NonNull Process processToAdd) throws AcceptProcessException {
        checkIfAlreadyActive(processToAdd);

        LocalDateTime timeStamp = LocalDateTime.now();

        ProcessDescriptor descriptor = new ProcessDescriptor(processToAdd, timeStamp);

        activeProcessesMap.put(processToAdd.getPid(), descriptor);
    }

    @Override
    public synchronized List<Process> getRunningProcesses() {
        // Iterator of a treeSet returns items in order
        return getRunningProcesses(defaultProcessOrdering);
    }

    @Override
    public synchronized List<Process> getRunningProcesses(@NonNull ProcessOrdering customOrdering) {
        // Iterator of a treeSet returns items in order
        return activeProcessesMap.values().stream()
                .sorted(customOrdering.getComparingStrategy())
                .map(d -> d.getProcess())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void kill(@NonNull Process processToKill) throws KillProcessException, ProcessNotFoundException {
        ProcessDescriptor descriptor = Optional.ofNullable(activeProcessesMap.get(processToKill.getPid()))
                .orElseThrow(() -> new ProcessNotFoundException(String.format("Process with PID %s not active", processToKill.getPid())));

        killProcessInternal(descriptor);
    }

    @Override
    public synchronized void killGroup(@NonNull Priority priorityGroup) throws KillProcessException {
        List<ProcessDescriptor> processesToKill = activeProcessesMap.values().stream()
                .filter(p -> priorityGroup.equals(p.getProcess().getPriority()))
                .collect(Collectors.toList());


        processesToKill.forEach(this::killProcessInternal);
    }

    @Override
    public synchronized void killAll() throws KillProcessException {
        List<ProcessDescriptor> processesToKill = activeProcessesMap.values().stream().collect(Collectors.toList());

        processesToKill.forEach(this::killProcessInternal);
    }

    protected void checkIfAlreadyActive(Process processToAdd) throws ProcessAlreadyActiveException {
        if (activeProcessesMap.get(processToAdd.getPid()) != null) {
            throw new ProcessAlreadyActiveException(String.format("Process with PID %s is already active", processToAdd.getPid()));
        }
    }

    private synchronized void killProcessInternal(@NonNull ProcessDescriptor processDescriptor) {
        boolean isKillSucceded = processDescriptor.getProcess().acceptSignal(Signal.KILL);

        if (isKillSucceded) {
            activeProcessesMap.remove(processDescriptor.getProcess().getPid());
        }
    }
}
