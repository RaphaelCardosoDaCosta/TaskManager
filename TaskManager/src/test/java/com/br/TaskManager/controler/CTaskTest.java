package com.br.TaskManager.controler;

import com.br.TaskManager.dtos.TaskDto;
import com.br.TaskManager.model.Task;
import com.br.TaskManager.service.STask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CTaskTest {

    @InjectMocks
    private CTask cTask;

    @Mock
    private STask serviceTask;

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setId("1");
        task.setTitle("Nova tarefa");
        task.setDescription("Descrição");
        task.setStatus("TODO");
        task.setPriority("ALTA");

        when(serviceTask.create(any(Task.class))).thenReturn(task);

        ResponseEntity<Map<String, Object>> response = cTask.createTask(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("data"));

        Task result = (Task) response.getBody().get("data");
        assertEquals("Nova tarefa", result.getTitle());
    }

    @Test
    void testGetTasks() {
        Task task1 = new Task();
        task1.setId("1");
        task1.setTitle("Tarefa 1");

        Task task2 = new Task();
        task2.setId("2");
        task2.setTitle("Tarefa 2");

        List<Task> tasks = List.of(task1, task2);
        when(serviceTask.getAll()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = cTask.getTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId("1");
        task.setTitle("Buscar por ID");

        when(serviceTask.getById("1")).thenReturn(Optional.of(task));

        ResponseEntity<Optional<Task>> response = cTask.getTaskById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals("1", response.getBody().get().getId());
    }

    @Test
    void testDeleteTask() {
        when(serviceTask.delete("1")).thenReturn("Tarefa excluída com sucesso");

        ResponseEntity<String> response = cTask.deleteTask("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarefa excluída com sucesso", response.getBody());
    }

    @Test
    void testUpdateTaskSuccess() {
        Task updated = new Task();
        updated.setId("1");
        updated.setTitle("Atualizada");
        updated.setDescription("Descrição atualizada");
        updated.setStatus("DONE");
        updated.setPriority("ALTA");

        TaskDto dto = new TaskDto(
                "Atualizada",
                "Descrição atualizada",
                "DONE",
                "ALTA"
        );

        when(serviceTask.update(eq("1"), any(TaskDto.class))).thenReturn(Optional.of(updated));

        ResponseEntity<Task> response = cTask.updateTask("1", dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Atualizada", response.getBody().getTitle());
    }

    @Test
    void testUpdateTaskNotFound() {
        TaskDto dto = new TaskDto(
                "Atualizada",
                "Descrição atualizada",
                "DONE",
                "ALTA"
        );

        when(serviceTask.update(eq("1"), any(TaskDto.class))).thenReturn(Optional.empty());

        ResponseEntity<Task> response = cTask.updateTask("1", dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(serviceTask.getById("999")).thenReturn(Optional.empty());

        ResponseEntity<Optional<Task>> response = cTask.getTaskById("999");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

}

