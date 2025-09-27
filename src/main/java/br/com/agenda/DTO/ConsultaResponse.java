package br.com.agenda.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ConsultaResponse {
    private Long id;
    private LocalDateTime dataConsulta;
    private String paciente;
    private String observacoes;
    private String medicoNome;
    private Long medicoId;
}
