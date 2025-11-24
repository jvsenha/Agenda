package br.com.agenda.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExameDTO {
    private Long id;
    @NotBlank(message = "O nome do exame é obrigatório")
    private String nome;
    @NotBlank(message = "O tipo de exame é obrigatório")
    private String tipo;
}