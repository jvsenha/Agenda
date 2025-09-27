package br.com.agenda.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AgendaCompletaDTO {
    private Long idMedico;
    private String nomeMedico;
    private String especialidade;
    private List<ConsultaResponse> consultas;
}
