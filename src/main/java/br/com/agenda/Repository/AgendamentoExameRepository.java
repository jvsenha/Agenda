package br.com.agenda.Repository;

import br.com.agenda.Entity.AgendamentoExameEntity;
import br.com.agenda.Entity.ExameEntity;
import br.com.agenda.Entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoExameRepository extends JpaRepository<AgendamentoExameEntity, Long> {
    boolean existsByPacienteAndExameAndDataExame(PacienteEntity paciente, ExameEntity exame, LocalDateTime dataExame);
}