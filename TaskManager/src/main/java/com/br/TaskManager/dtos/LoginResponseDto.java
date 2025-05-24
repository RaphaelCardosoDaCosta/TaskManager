package com.br.TaskManager.dtos;

public record LoginResponseDto(
    String message,
    Object data
) {
    public LoginResponseDto(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
