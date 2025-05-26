import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HeaderComponent } from "../header/header.component";
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule, HeaderComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email = '';
  senha = '';

  errorMessage='';

  constructor(private serviceLogin: LoginService, private router: Router) {}

  onSubmit() {

    const userData = {
      email: this.email,
      senha: this.senha
    }

    this.serviceLogin.postLogin(userData).subscribe({
      next: (res) => {
        console.log("bem-vindo");
        alert("Bem-vindo");

        this.router.navigate(["/dashboard"]);
      },
      error: (err) => {
        this.errorMessage = err.message;
        console.log("erro ao logar" + err);


      }

    });

    console.log('Login enviado:', this.email, this.senha);
  }
}
