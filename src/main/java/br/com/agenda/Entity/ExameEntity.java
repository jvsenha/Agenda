package br.com.agenda.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ExameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do exame é obrigatório")
    @Column(unique = true, nullable = false)
    private String nome;

    @NotBlank(message = "Tipo do exame é obrigatório")
    @Column(nullable = false)
    private String tipo;

    @ManyToMany(mappedBy = "exames")
    private List<PacienteEntity> pacientes;
}