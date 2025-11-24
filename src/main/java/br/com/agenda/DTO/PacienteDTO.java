package br.com.agenda.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}