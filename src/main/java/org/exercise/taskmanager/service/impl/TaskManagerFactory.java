package org.exercise.taskmanager.service.impl;

import org.exercise.taskmanager.service.TaskManagerService;

public final class TaskManagerFactory {

    // the example asks for an amount decided at compile time, not via configuration
    private static int MAX_NUMBER_CONCURRENT_PROCESSES = 7;
    // I hard coded the client as an example. It should be chosen differently, loading config files (or environment vars)
    private static String CLIENT = "Corporate3";

    private static TaskManagerService singletonService;

    public static TaskManagerService getTaskManager() {
        if (singletonService == null) {
            switch (CLIENT) {
                case "Corporate1":
                    singletonService = new PriorityBasedTaskManagerService(MAX_NUMBER_CONCURRENT_PROCESSES);
                    break;
                case "Corporate2":
                    singletonService = new FifoTaskManagerService(MAX_NUMBER_CONCURRENT_PROCESSES);
                    break;
                default:
                    //Default behavior as specified in the exercise
                    singletonService = new RejectIfFullTaskManagerService(MAX_NUMBER_CONCURRENT_PROCESSES);
            }
        }
        return singletonService;
    }
}
