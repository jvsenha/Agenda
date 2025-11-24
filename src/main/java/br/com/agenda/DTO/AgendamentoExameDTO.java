package br.com.agenda.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoExameDTO {
    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    private Long pacienteId;
    private String nomePaciente; // Para exibição

    @NotNull(message = "Exame é obrigatório")
    private Long exameId;
    private String nomeExame; // Para exibição

    @NotNull(message = "Data do exame é obrigatória")
    private LocalDateTime dataExame;

    private String observacoes;
}