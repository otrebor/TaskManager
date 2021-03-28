package org.exercise.taskmanager;

import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.service.TaskManagerService;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;
import org.exercise.taskmanager.service.impl.TaskManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Application {
    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("Let's manage these tasks");

            TaskManagerService taskManagerService = TaskManagerFactory.getTaskManager();

            List<Process> processes = new ArrayList<>();
            int i = 0;
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.MEDIUM));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.LOW));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.MEDIUM));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.HIGH));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.HIGH));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.MEDIUM));
            processes.add(new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a" + (i++)), Priority.LOW));


            addProcess(processes, 0, taskManagerService);
            addProcess(processes, 1, taskManagerService);
            addProcess(processes, 2, taskManagerService);
            addProcess(processes, 3, taskManagerService);
            addProcess(processes, 4, taskManagerService);

            printActiveProcesses(taskManagerService);

            killProcess(processes, 2, taskManagerService);

            printActiveProcesses(taskManagerService);

            killGroup(Priority.MEDIUM, taskManagerService);

            printActiveProcesses(taskManagerService);

            killAll(taskManagerService);

            printActiveProcesses(taskManagerService);

        } catch (Throwable t) {
            LOGGER.error("Error executing Application. {}", t.getMessage());
            LOGGER.debug("Exception: {}", t.getMessage(), t);
        }
    }

    private static void printActiveProcesses(TaskManagerService taskManagerService) {
        LOGGER.info("Active processes: \n" + taskManagerService.getRunningProcesses().stream()
                .map(p -> p.toString())
                .collect(Collectors.joining("\n")));
    }

    private static void addProcess(List<Process> processes, int index, TaskManagerService taskManagerService) throws AcceptProcessException {
        LOGGER.info("Adding process {}", processes.get(index).getPid());
        taskManagerService.addProcess(processes.get(index));
    }

    private static void killProcess(List<Process> processes, int index, TaskManagerService taskManagerService) throws KillProcessException, ProcessNotFoundException {
        LOGGER.info("Killing process {}", processes.get(index).getPid());
        taskManagerService.kill(processes.get(index));
    }

    private static void killGroup(Priority priority, TaskManagerService taskManagerService) throws KillProcessException {
        LOGGER.info("Killing process group {}", priority.name());
        taskManagerService.killGroup(priority);
    }

    private static void killAll(TaskManagerService taskManagerService) throws KillProcessException {
        LOGGER.info("Killing all processes ");
        taskManagerService.killAll();
    }
}
