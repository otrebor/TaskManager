package org.exercise.taskmanager.service.impl;


import org.assertj.core.api.Assertions;
import org.exercise.taskmanager.model.process.Priority;
import org.exercise.taskmanager.model.process.Process;
import org.exercise.taskmanager.service.exception.AcceptProcessException;
import org.exercise.taskmanager.service.exception.KillProcessException;
import org.exercise.taskmanager.service.exception.ProcessNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class FifoTaskManagerServiceMethodKillTest extends FifoTaskManagerServiceTestBase {

    @Test
    @DisplayName("Should kill process correctly")
    public void testKill() throws AcceptProcessException, KillProcessException, ProcessNotFoundException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.MEDIUM);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.LOW);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.HIGH);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        serviceToTest.kill(process2);

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(4)
                .containsExactly(process1, process3, process4, process5);
    }

    @Test
    @DisplayName("Should kill process group correctly")
    public void testKillGroup() throws AcceptProcessException, KillProcessException, ProcessNotFoundException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.MEDIUM);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.LOW);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.HIGH);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        serviceToTest.killGroup(Priority.MEDIUM);

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(2)
                .containsExactly(process3, process4);
    }

    @Test
    @DisplayName("Should kill all processes correctly")
    public void testKillAll() throws AcceptProcessException, KillProcessException, ProcessNotFoundException {

        Process process1 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a1"), Priority.MEDIUM);
        serviceToTest.addProcess(process1);

        Process process2 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a2"), Priority.MEDIUM);
        serviceToTest.addProcess(process2);

        Process process3 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a3"), Priority.LOW);
        serviceToTest.addProcess(process3);

        Process process4 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a4"), Priority.HIGH);
        serviceToTest.addProcess(process4);

        Process process5 = new Process(UUID.fromString("294f85fa-5817-4826-a658-13e8aa6c29a5"), Priority.MEDIUM);
        serviceToTest.addProcess(process5);

        serviceToTest.killAll();

        List<Process> list = serviceToTest.getRunningProcesses();

        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(0);
    }
}