import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

interface Login {
  email: string,
  senha: string
}

@Injectable({ providedIn: 'root' })
export class LoginService {

  private apiUrl = "http://localhost:8080/login"

  constructor(private http: HttpClient) { }

  postLogin(login: Login): Observable<any> {
    return this.http.post<any>(this.apiUrl, login).pipe(catchError(this.handleError));
  }


  private handleError(error: HttpErrorResponse) {
    if (error.status === 401) {
      return throwError(() => new Error('E-mail ou senha inválidos.'));
    } else {
      return throwError(() => new Error('Erro no servidor. Tente novamente mais tarde.'));
    }
  }


}
