package org.exercise.taskmanager.service;

import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.model.taskmanager.ProcessOrdering;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;

import java.util.List;

public interface TaskManagerService {

    void addProcess(Process processToAdd) throws AcceptProcessException;

    List<Process> getRunningProcesses();
    
    List<Process> getRunningProcesses(ProcessOrdering customOrdering);

    void kill(Process processToKill) throws KillProcessException, ProcessNotFoundException;

    void killGroup(Priority priorityGroup) throws KillProcessException;

    void killAll() throws KillProcessException;
}
