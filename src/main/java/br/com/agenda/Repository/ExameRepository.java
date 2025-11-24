package br.com.agenda.Repository;

import br.com.agenda.Entity.ExameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<ExameEntity, Long> {
}