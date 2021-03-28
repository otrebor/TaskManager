package org.exercise.taskmanager.service.impl;


import org.junit.jupiter.api.BeforeEach;

class PriorityBasedTaskManagerServiceTestBase {

    private static int MAX_NUMBER_ALLOWED_PROCESSES = 5;

    protected PriorityBasedTaskManagerService serviceToTest;

    @BeforeEach
    public void setUp() {
        serviceToTest = new PriorityBasedTaskManagerService(MAX_NUMBER_ALLOWED_PROCESSES);
    }
}