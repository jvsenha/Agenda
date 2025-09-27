package br.com.agenda.DTO;

import lombok.Data;

@Data
public class MedicoResponse {
    private Long id;
    private String nome;
    private String especialidade;
}
