package br.com.agenda.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat; // <-- IMPORT THIS

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ConsultaRequest {

    private LocalDateTime dataConsulta;

    private String paciente;
    private String observacoes;
    private Long medicoId;
    private Long id;
}