package br.com.agenda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {

    @NotNull(message = "A data da consulta é obrigatória")
    private LocalDateTime dataConsulta;

    @NotBlank(message = "O nome do paciente é obrigatório")
    private String paciente;

    private String observacoes;

    @NotNull(message = "O médico é obrigatório")
    private Long medicoId;

    private Long id;
}