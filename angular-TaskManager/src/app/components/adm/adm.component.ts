import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Task, TaskService } from '../../services/task.service';

@Component({
  selector: 'app-adm',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './adm.component.html',
  styleUrl: './adm.component.scss'
})
export class AdmComponent {

  title = '';
  description = '';
  status = '';
  priority = '';


  constructor(private serviceTask: TaskService, private route: Router) { }

  task: Task = {
    title: this.title,
    description: this.description,
    status: this.status,
    priority: this.priority
  }


  onSubmit() {
    // const taskData = {
    //   title: this.title,
    //   description: this.description,
    //   status: this.status,
    //   priority: this.priority
    // }

    this.serviceTask.postTask(this.task).subscribe({
      next: (res) => {
        alert("Tarefa criada.");
        console.log("tarefa criada." + res);

        this.route.navigate(['/dashboard']);
      },
      error(err) {
        alert("erro ao cadastrar tarefa.");
        console.log("erro ao cadastrar tarefa" + err);
      },
    });

  }



}
