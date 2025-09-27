package br.com.agenda.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ConsultaResponse {
    private Long id;
    private Date dataConsulta;
    private String paciente;
    private String observacoes;
}
