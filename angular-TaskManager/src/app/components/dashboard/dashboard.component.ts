import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Task, TaskSchema, TaskService } from '../../services/task.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {


  tasks: TaskSchema[] = [];
  editingTask: TaskSchema | null = null;

  constructor(private taskService: TaskService) { }

  ngOnInit(): void {
    this.taskService.getTasks().subscribe({
      next: (data) => {
        this.tasks = data;
        this.filteredTasks = [...this.tasks]; // exibir todas inicialmente
      },
      error: (err) => console.error('Erro ao carregar tarefas', err),
    });
  }

  loadTasks() {
    this.taskService.getTasks().subscribe({
      next: (res) => this.tasks = res,
      error: (err) => console.error('Erro ao carregar tarefas:', err)
    });
  }

  filterStatus: string = '';
  filterPriority: string = '';

  filteredTasks: TaskSchema[] = [];

  applyFilters() {
    this.filteredTasks = this.tasks.filter((task) => {
      const matchesStatus = this.filterStatus ? task.status === this.filterStatus : true;
      const matchesPriority = this.filterPriority ? task.priority === this.filterPriority : true;
      return matchesStatus && matchesPriority;
    });
  }

  deleteTask(id: string) {
    if (confirm('Tem certeza que deseja excluir?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => this.loadTasks(),
        error: (err) => console.error('Erro ao excluir:', err)
      });
      window.location.reload();
    }
  }

  startEdit(task: Task) {
    this.editingTask = { ...task };
  }

  cancelEdit() {
    this.editingTask = null;
  }

  updateTask() {
    if (!this.editingTask) return;

    this.taskService.updateTask(this.editingTask.id!, this.editingTask).subscribe({
      next: () => {
        this.editingTask = null;
        this.loadTasks();
        window.location.reload();
      },
      error: (err) => console.error('Erro ao atualizar:', err)
    });
  }

}
