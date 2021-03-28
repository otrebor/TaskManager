package org.exercise.taskmanager.service.impl;


import org.junit.jupiter.api.BeforeEach;

class RejectIfFullTaskManagerServiceTestBase {

    private static int MAX_NUMBER_ALLOWED_PROCESSES = 5;

    protected RejectIfFullTaskManagerService serviceToTest;

    @BeforeEach
    public void setUp() {
        serviceToTest = new RejectIfFullTaskManagerService(MAX_NUMBER_ALLOWED_PROCESSES);
    }
}