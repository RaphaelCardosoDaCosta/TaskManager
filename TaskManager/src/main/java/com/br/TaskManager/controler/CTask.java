package com.br.TaskManager.controler;

import com.br.TaskManager.dtos.TaskDto;
import com.br.TaskManager.model.Task;
import com.br.TaskManager.service.STask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/tasks")
public class CTask {

    public final STask serviceTask;

    public CTask(STask serviceTask) {
        this.serviceTask = serviceTask;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Task body) {
        Task taskCreated = serviceTask.create(body);

        Map<String, Object> response = new HashMap<>();
        response.put("data", taskCreated);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok().body(serviceTask.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok().body(serviceTask.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        return ResponseEntity.ok().body(serviceTask.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody TaskDto body) {
        return serviceTask.update(id, body).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
