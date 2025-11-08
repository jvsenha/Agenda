package br.com.agenda.Service;

import br.com.agenda.DTO.PacienteDTO;
import br.com.agenda.Entity.PacienteEntity;
import br.com.agenda.Mapper.PacienteMapper;
import br.com.agenda.Repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PacienteDTO> buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .map(PacienteMapper::toDTO);
    }

    public PacienteDTO salvar(PacienteDTO pacienteDTO) {
        PacienteEntity pacienteEntity = PacienteMapper.toEntity(pacienteDTO);
        PacienteEntity pacienteSalvo = pacienteRepository.save(pacienteEntity);
        return PacienteMapper.toDTO(pacienteSalvo);
    }

    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

    public PacienteDTO atualizar(Long id, PacienteDTO pacienteDTO) {
        PacienteEntity pacienteEntity = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o ID: " + id));
        pacienteEntity.setNome(pacienteDTO.getNome());
        pacienteEntity.setCpf(pacienteDTO.getCpf());
        PacienteEntity pacienteAtualizado = pacienteRepository.save(pacienteEntity);
        return PacienteMapper.toDTO(pacienteAtualizado);
    }
}