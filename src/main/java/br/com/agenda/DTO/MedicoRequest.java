package br.com.agenda.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedicoRequest {
    private Long id;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;
}