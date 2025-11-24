package br.com.agenda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {

    @NotNull(message = "A data da consulta é obrigatória")
    private LocalDateTime dataConsulta;

    @NotNull(message = "O paciente é obrigatório")
    private Long pacienteId;

    private String observacoes;

    @NotNull(message = "O médico é obrigatório")
    private Long medicoId;

    private Long id;
}