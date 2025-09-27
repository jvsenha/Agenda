package br.com.agenda.Service;

import br.com.agenda.DTO.AgendaCompletaDTO;
import br.com.agenda.DTO.MedicoRequest;
import br.com.agenda.DTO.MedicoResponse;
import br.com.agenda.Entity.MedicoEntity;
import br.com.agenda.Mapper.MedicoMapper;
import br.com.agenda.Repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<MedicoResponse> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(MedicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<MedicoResponse> buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .map(MedicoMapper::toResponseDTO);
    }

    public MedicoResponse salvar(MedicoRequest medicoRequest) {
        MedicoEntity medicoEntity = MedicoMapper.toEntity(medicoRequest);
        MedicoEntity medicoSalvo = medicoRepository.save(medicoEntity);
        return MedicoMapper.toResponseDTO(medicoSalvo);
    }

    public void deletar(Long id) {
        medicoRepository.deleteById(id);
    }

    public List<AgendaCompletaDTO> getAgendaCompleta() {
        return medicoRepository.findAll()
                .stream()
                .map(MedicoMapper::toAgendaCompletaDTO)
                .collect(Collectors.toList());
    }

    public MedicoResponse atualizar(Long id, MedicoRequest medicoRequest) {
        MedicoEntity medicoEntity = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com o ID: " + id));
        medicoEntity.setNome(medicoRequest.getNome());
        medicoEntity.setEspecialidade(medicoRequest.getEspecialidade());
        MedicoEntity medicoAtualizado = medicoRepository.save(medicoEntity);
        return MedicoMapper.toResponseDTO(medicoAtualizado);
    }
}