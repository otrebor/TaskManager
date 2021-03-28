package org.exercise.taskmanager.service.impl;


import org.assertj.core.api.Assertions;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.ProcessAlreadyActiveException;
import org.exercise.taskmanager.service.exception.TooManyConcurrentProcessesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class RejectIfFullTaskManagerServiceMethodAddTest extends RejectIfFullTaskManagerServiceTestBase {

    @Test
    @DisplayName("Should add processes correctly and return them in insertion order")
    public void testAdd() throws AcceptProcessException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.MEDIUM);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.MEDIUM);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.MEDIUM);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(5)
                .containsExactly(process1, process2, process3, process4, process5);
    }

    @Test()
    @DisplayName("Should add processes correctly until max capacity, then throw exception")
    public void testAdd2() throws AcceptProcessException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.MEDIUM);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.MEDIUM);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.MEDIUM);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        Process process6 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a6"), Priority.MEDIUM);

        org.junit.jupiter.api.Assertions.assertThrows(TooManyConcurrentProcessesException.class, () -> {
            serviceToTest.addProcess(process6);
        });
    }

    @Test()
    @DisplayName("Should throw exception when input is null")
    public void testAdd3() throws AcceptProcessException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        org.junit.jupiter.api.Assertions.assertThrows(ProcessAlreadyActiveException.class, () -> {
            serviceToTest.addProcess(process1);
        });
    }

    @Test()
    @DisplayName("Should throw exception when input is added twice")
    public void testAdd4() throws AcceptProcessException {
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            serviceToTest.addProcess(null);
        });
    }
}