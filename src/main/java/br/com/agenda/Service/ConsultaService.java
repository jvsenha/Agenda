package br.com.agenda.Service;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.DTO.ConsultaResponse;
import br.com.agenda.Entity.ConsultaEntity;
import br.com.agenda.Entity.MedicoEntity;
import br.com.agenda.Entity.PacienteEntity;
import br.com.agenda.Mapper.ConsultaMapper;
import br.com.agenda.Repository.ConsultaRepository;
import br.com.agenda.Repository.MedicoRepository;
import br.com.agenda.Repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<ConsultaResponse> listarTodas() {
        return consultaRepository.findAll()
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConsultaResponse> buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .map(ConsultaMapper::toResponseDTO);
    }

    public ConsultaResponse salvar(ConsultaRequest consultaRequest) {
        MedicoEntity medico = medicoRepository.findById(consultaRequest.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));

        PacienteEntity paciente = pacienteRepository.findById(consultaRequest.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // 1. Valida Médico Ocupado
        if (consultaRepository.existsByMedicoAndDataConsulta(medico, consultaRequest.getDataConsulta())) {
            throw new IllegalArgumentException("Conflito: O Médico já possui agendamento neste horário.");
        }

        // 2. NOVO: Valida Paciente Ocupado (ou consulta duplicada)
        if (consultaRepository.existsByPacienteAndDataConsulta(paciente, consultaRequest.getDataConsulta())) {
            throw new IllegalArgumentException("Conflito: O Paciente já possui uma consulta agendada neste horário.");
        }

        ConsultaEntity consultaEntity = ConsultaMapper.toEntity(consultaRequest, medico, paciente);
        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);
        return ConsultaMapper.toResponseDTO(consultaSalva);
    }

    public ConsultaResponse atualizar(Long id, ConsultaRequest consultaRequest) {
        ConsultaEntity consultaEntity = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        MedicoEntity medico = medicoRepository.findById(consultaRequest.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));

        PacienteEntity paciente = pacienteRepository.findById(consultaRequest.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Verifica conflito apenas se a data mudou
        if (!consultaEntity.getDataConsulta().isEqual(consultaRequest.getDataConsulta())) {
            if (consultaRepository.existsByMedicoAndDataConsulta(medico, consultaRequest.getDataConsulta())) {
                throw new IllegalArgumentException("Médico indisponível neste horário.");
            }
            if (consultaRepository.existsByPacienteAndDataConsulta(paciente, consultaRequest.getDataConsulta())) {
                throw new IllegalArgumentException("Paciente já possui compromisso neste horário.");
            }
        }

        consultaEntity.setPaciente(paciente);
        consultaEntity.setDataConsulta(consultaRequest.getDataConsulta());
        consultaEntity.setObservacoes(consultaRequest.getObservacoes());
        consultaEntity.setMedico(medico);

        ConsultaEntity consultaAtualizada = consultaRepository.save(consultaEntity);
        return ConsultaMapper.toResponseDTO(consultaAtualizada);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }
}