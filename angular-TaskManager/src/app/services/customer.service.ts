import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


interface Customer {
  nome: string;
  cpf: string;
  email: string;
  senha: string;
}


@Injectable({providedIn: 'root'})
export class CustomerService {

  private apiUrl = 'http://localhost:8080/register';

  constructor(private http: HttpClient) { }

  register(customer: Customer): Observable<any> {
    return this.http.post(this.apiUrl, customer);
  }
}
