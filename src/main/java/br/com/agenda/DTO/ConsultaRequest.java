package br.com.agenda.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ConsultaRequest {
    private Date dataConsulta;
    private String paciente;
    private String observacoes;
    private Long medicoId;
}
