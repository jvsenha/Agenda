package br.com.agenda.Repository;

import br.com.agenda.Entity.ConsultaEntity;
import br.com.agenda.Entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    boolean existsByMedicoAndDataConsulta(MedicoEntity medico, LocalDateTime dataConsulta);
}
