package org.exercise.taskmanager.service.impl;

import org.assertj.core.api.Assertions;
import org.exercise.taskmanager.service.TaskManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskManagerFactoryTest {

    @Test
    @DisplayName("Should provide always the same task manager")
    public void test1() {
        TaskManagerService taskManagerService = TaskManagerFactory.getTaskManager();
        Assertions.assertThat(taskManagerService)
                .isNotNull()
                .isEqualTo(TaskManagerFactory.getTaskManager());
    }
}