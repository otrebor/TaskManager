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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FifoTaskManagerService extends TaskManagerServiceBase {

    //— a bounded FIFO blocking queue backed by an array
    private final ArrayBlockingQueue<ProcessDescriptor> fifoQueue;

    public FifoTaskManagerService(int maxNumberAllowedProcesses) {
        super(maxNumberAllowedProcesses, ProcessOrdering.FIFO);
        fifoQueue = new ArrayBlockingQueue(maxNumberAllowedProcesses);
    }

    @Override
    public synchronized void addProcess(Process processToAdd) throws AcceptProcessException {
        checkIfAlreadyActive(processToAdd);

        if (fifoQueue.size() == maxNumberAllowedProcesses) {
            ProcessDescriptor out = fifoQueue.remove();
            try {
                kill(out.getProcess());
            } catch (Exception e) {
                throw new AcceptProcessException("Error while killing process", e);
            }
        }

        super.addProcess(processToAdd);
        fifoQueue.add(activeProcessesMap.get(processToAdd.getPid()));
    }

    @Override
    public synchronized List<Process> getRunningProcesses() {
        // Iterator of a treeSet returns items in order
        return StreamSupport.stream(fifoQueue.spliterator(), false)
                .map(d -> d.getProcess())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void kill(@NonNull Process processToKill) throws KillProcessException, ProcessNotFoundException {
        ProcessDescriptor descriptor = Optional.ofNullable(activeProcessesMap.get(processToKill.getPid()))
                .orElseThrow(() -> new ProcessNotFoundException(String.format("process with PID %s not found", processToKill.getPid())));

        super.kill(processToKill);

        fifoQueue.remove(descriptor);
    }

    @Override
    public synchronized void killGroup(@NonNull Priority priorityGroup) throws KillProcessException {
        List<ProcessDescriptor> processesInGroup = activeProcessesMap.values().stream()
                .filter(p -> priorityGroup.equals(p.getProcess().getPriority()))
                .collect(Collectors.toList());

        super.killGroup(priorityGroup);
        fifoQueue.removeAll(processesInGroup);
    }

    @Override
    public synchronized void killAll() throws KillProcessException {
        super.killAll();
        fifoQueue.clear();
    }
}
