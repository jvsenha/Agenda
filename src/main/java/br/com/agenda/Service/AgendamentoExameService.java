package br.com.agenda.Service;

import br.com.agenda.DTO.AgendamentoExameDTO;
import br.com.agenda.Entity.AgendamentoExameEntity;
import br.com.agenda.Entity.ExameEntity;
import br.com.agenda.Entity.PacienteEntity;
import br.com.agenda.Repository.AgendamentoExameRepository;
import br.com.agenda.Repository.ExameRepository;
import br.com.agenda.Repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoExameService {

    @Autowired private AgendamentoExameRepository repository;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private ExameRepository exameRepository;

    public List<AgendamentoExameDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void salvar(AgendamentoExameDTO dto) {
        AgendamentoExameEntity entity = new AgendamentoExameEntity();
        if (dto.getId() != null) entity.setId(dto.getId()); // Atualização

        PacienteEntity paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        ExameEntity exame = exameRepository.findById(dto.getExameId())
                .orElseThrow(() -> new EntityNotFoundException("Exame não encontrado"));

        entity.setPaciente(paciente);
        entity.setExame(exame);
        entity.setDataExame(dto.getDataExame());
        entity.setObservacoes(dto.getObservacoes());

        repository.save(entity);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private AgendamentoExameDTO toDTO(AgendamentoExameEntity entity) {
        AgendamentoExameDTO dto = new AgendamentoExameDTO();
        dto.setId(entity.getId());
        dto.setPacienteId(entity.getPaciente().getId());
        dto.setNomePaciente(entity.getPaciente().getNome());
        dto.setExameId(entity.getExame().getId());
        dto.setNomeExame(entity.getExame().getNome());
        dto.setDataExame(entity.getDataExame());
        dto.setObservacoes(entity.getObservacoes());
        return dto;
    }
}