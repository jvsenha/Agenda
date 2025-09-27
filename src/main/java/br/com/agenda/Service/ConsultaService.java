package br.com.agenda.Service;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.DTO.ConsultaResponse;
import br.com.agenda.Entity.ConsultaEntity;
import br.com.agenda.Entity.MedicoEntity;
import br.com.agenda.Mapper.ConsultaMapper;
import br.com.agenda.Repository.ConsultaRepository;
import br.com.agenda.Repository.MedicoRepository;
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
                .orElseThrow(() -> new EntityNotFoundException(
                        "Médico não encontrado com o ID: " + consultaRequest.getMedicoId())
                );

        ConsultaEntity consultaEntity = ConsultaMapper.toEntity(consultaRequest, medico);
        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);
        return ConsultaMapper.toResponseDTO(consultaSalva);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }

    public ConsultaResponse atualizar(Long id, ConsultaRequest consultaRequest) {
        ConsultaEntity consultaEntity = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id));
        MedicoEntity medico = medicoRepository.findById(consultaRequest.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com o ID: " + consultaRequest.getMedicoId()));
        consultaEntity.setPaciente(consultaRequest.getPaciente());
        consultaEntity.setDataConsulta(consultaRequest.getDataConsulta());
        consultaEntity.setObservacoes(consultaRequest.getObservacoes());
        consultaEntity.setMedico(medico);
        ConsultaEntity consultaAtualizada = consultaRepository.save(consultaEntity);
        return ConsultaMapper.toResponseDTO(consultaAtualizada);
    }
}