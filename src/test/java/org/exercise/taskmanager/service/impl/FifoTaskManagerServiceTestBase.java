package org.exercise.taskmanager.service.impl;


import org.junit.jupiter.api.BeforeEach;

class FifoTaskManagerServiceTestBase {

    private static int MAX_NUMBER_ALLOWED_PROCESSES = 5;

    protected FifoTaskManagerService serviceToTest;

    @BeforeEach
    public void setUp() {
        serviceToTest = new FifoTaskManagerService(MAX_NUMBER_ALLOWED_PROCESSES);
    }
}