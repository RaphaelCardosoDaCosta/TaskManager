package com.br.TaskManager.dtos;

public record CustomerDto(
        String nome,
        String cpf,
        String email,
        String senha
) {}
