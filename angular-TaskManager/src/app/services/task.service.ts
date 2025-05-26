import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Task {
  title: string,
  description: string,
  status: string,
  priority: string
}

export interface TaskSchema {

  id?: string;
  title: string;
  description: string;
  status: string;
  priority: string;
}


@Injectable({ providedIn: 'root' })
export class TaskService {

  private apiUrl = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) { }

  postTask(task: Task): Observable<any> {
    return this.http.post(this.apiUrl, task);
  }

  getTasks(): Observable<TaskSchema[]> {
    return this.http.get<TaskSchema[]>(this.apiUrl);
  }

  getTask(id: string): Observable<TaskSchema> {
    return this.http.get<TaskSchema>(`${this.apiUrl}/${id}`);
  }

  updateTask(id: string, task: Task): Observable<Task> {
    return this.http.patch<Task>(`${this.apiUrl}/${id}`, task);
  }

  deleteTask(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
