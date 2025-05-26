import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router, RouterModule } from '@angular/router';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-register',
  imports: [HeaderComponent, RouterModule, NgIf, FormsModule],
  standalone: true,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {


  constructor(private customerService: CustomerService, private router: Router) {};

  nome = '';
  cpf = '';
  email = '';
  senha = '';



  cpfValido(): boolean {
    // Regex para validar CPF no formato 999.999.999-99
    return /^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(this.cpf);
  }

  emailValido(): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email); // validação simples de e-mail
  }

  onCpfChange(value: string) {
    const digits = value.replace(/\D/g, '').slice(0, 11); // remove não dígitos

    // Formata como 999.999.999-99
    let formatted = '';
    if (digits.length > 0) {
      formatted = digits.slice(0, 3);
    }
    if (digits.length > 3) {
      formatted += '.' + digits.slice(3, 6);
    }
    if (digits.length > 6) {
      formatted += '.' + digits.slice(6, 9);
    }
    if (digits.length > 9) {
      formatted += '-' + digits.slice(9, 11);
    }

    this.cpf = formatted;
  }

  onSubmit() {
    if (!this.cpfValido() || !this.emailValido()) {
      alert('CPF ou Email inválido.');
      return;
    }
    const userData = {
      nome: this.nome,
      cpf: this.cpf,
      email: this.email,
      senha: this.senha
    };

    this.customerService.register(userData).subscribe({
      next: (res) => {
        console.log('Usuário cadastrado com sucesso!', res);
        alert("Usuario cadastrado.");

        this.router.navigate(["/dashboard"]);

        // redirecionar ou resetar formulário aqui se quiser
      },
      error: (err) => {
        alert("erro ao cadastrar.");
        console.error('Erro ao cadastrar usuário:', err);
      }
    });
  }

}
