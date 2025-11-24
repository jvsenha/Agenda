package br.com.agenda.Repository;

import br.com.agenda.Entity.AgendamentoExameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoExameRepository extends JpaRepository<AgendamentoExameEntity, Long> {
}