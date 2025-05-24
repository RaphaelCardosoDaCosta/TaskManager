package com.br.TaskManager.repository;

import com.br.TaskManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITask extends JpaRepository<Task, String> {
}
