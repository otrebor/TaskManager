package org.exercise.taskmanager.service.impl;


import org.assertj.core.api.Assertions;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class FifoTaskManagerServiceMethodAddTest extends FifoTaskManagerServiceTestBase {

    @Test
    @DisplayName("Should add processes correctly and return them in insertion order")
    public void testAdd() throws AcceptProcessException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.LOW);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.MEDIUM);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.HIGH);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(5)
                .containsExactly(process1, process2, process3, process4, process5);
    }

    @Test
    @DisplayName("Should add processes correctly and kill the ones with added first when full")
    public void testAdd1() throws AcceptProcessException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.LOW);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.MEDIUM);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.HIGH);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        Process process6 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a6"), Priority.MEDIUM);
        serviceToTest.addProcess(process6);

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(5)
                .containsExactly(process2, process3, process4, process5, process6);
    }
}