package org.exercise.taskmanager.service.impl;

import lombok.NonNull;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.model.taskmanager.ProcessDescriptor;
import org.exercise.taskmanager.model.taskmanager.ProcessOrdering;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;
import org.exercise.taskmanager.service.exception.TooManyConcurrentProcessesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RejectIfFullTaskManagerService extends TaskManagerServiceBase {

    private static Logger LOGGER = LoggerFactory.getLogger(RejectIfFullTaskManagerService.class);
    // not synchronized
    protected final TreeSet<ProcessDescriptor> processes;

    public RejectIfFullTaskManagerService(int maxNumberAllowedProcesses) {
        super(maxNumberAllowedProcesses, ProcessOrdering.ACTIVATION_TIME);
        this.processes = new TreeSet<>(ProcessOrdering.ACTIVATION_TIME.getComparingStrategy());
    }

    @Override
    public synchronized List<Process> getRunningProcesses() {
        LOGGER.debug("List size: {}", processes.size());
        // Iterator of a treeSet returns items in order
        return StreamSupport.stream(processes.spliterator(), false)
                .map(d -> d.getProcess())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void addProcess(@NonNull Process processToAdd) throws AcceptProcessException {
        if (activeProcessesMap.size() == maxNumberAllowedProcesses) {
            throw new TooManyConcurrentProcessesException("Reached maximum process capacity");
        }

        super.addProcess(processToAdd);
        ProcessDescriptor descriptor = activeProcessesMap.get(processToAdd.getPid());
        processes.add(descriptor);
        LOGGER.debug("Added process: {}. Collection size: {} {}", descriptor, processes.size(), activeProcessesMap.size());
    }

    @Override
    public synchronized void kill(@NonNull Process processToKill) throws KillProcessException, ProcessNotFoundException {
        ProcessDescriptor descriptor = Optional.ofNullable(activeProcessesMap.get(processToKill.getPid()))
                .orElseThrow(() -> new ProcessNotFoundException(String.format("process with PID %s not found", processToKill.getPid())));

        super.kill(processToKill);

        processes.remove(descriptor);
    }

    @Override
    public synchronized void killGroup(@NonNull Priority priorityGroup) throws KillProcessException {
        List<ProcessDescriptor> processesInGroup = activeProcessesMap.values().stream()
                .filter(p -> priorityGroup.equals(p.getProcess().getPriority()))
                .collect(Collectors.toList());

        super.killGroup(priorityGroup);
        processes.removeAll(processesInGroup);
    }

    @Override
    public synchronized void killAll() throws KillProcessException {
        super.killAll();
        processes.clear();
    }
}
