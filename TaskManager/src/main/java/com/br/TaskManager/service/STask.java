package com.br.TaskManager.service;

import com.br.TaskManager.dtos.TaskDto;
import com.br.TaskManager.model.Task;
import com.br.TaskManager.repository.ITask;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class STask {

    private final ITask repositoryTasks;

    public STask(ITask repositoryTasks) {
        this.repositoryTasks = repositoryTasks;
    }

    public Task create(Task data) {
        return repositoryTasks.save(data);
    }

    public Optional<Task> getById(String id) {
        return repositoryTasks.findById(id);
    }

    public List<Task> getAll() {
        return repositoryTasks.findAll();
    }

    public Optional<Task> update(String id, TaskDto data) {
        return repositoryTasks.findById(id).map(task -> {
            if (data.title() != null) {
                task.setTitle(data.title());
            }   if (data.description() != null) {
                task.setDescription(data.description());
            }   if (data.priority() != null) {
                task.setPriority(data.priority());
            }   if (data.status() != null) {
                task.setStatus(data.status());
            }
            return repositoryTasks.save(task);
                });
    }

    public String delete(String id) {
        repositoryTasks.deleteById(id);
        return "Tarefa removida.";
    }


}
