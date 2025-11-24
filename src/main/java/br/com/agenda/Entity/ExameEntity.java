package br.com.agenda.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ExameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;

    // Relação N:N com PacienteEntity. Mapeia o lado "invés" da relação.
    @ManyToMany(mappedBy = "exames")
    private List<PacienteEntity> pacientes;
}