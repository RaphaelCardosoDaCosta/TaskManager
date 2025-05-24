package com.br.TaskManager.dtos;

public record TaskDto(
    String title,
    String description,
    String status,
    String priority
) {

}
