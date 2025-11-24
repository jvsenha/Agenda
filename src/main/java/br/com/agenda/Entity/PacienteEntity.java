package br.com.agenda.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // NOVO: Relação N:N com ExameEntity.
    @ManyToMany
    @JoinTable(
            name = "paciente_exame", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "exame_id")
    )
    private List<ExameEntity> exames;
}