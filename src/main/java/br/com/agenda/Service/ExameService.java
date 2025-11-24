package br.com.agenda.Service;

import br.com.agenda.DTO.ExameDTO;
import br.com.agenda.Entity.ExameEntity;
import br.com.agenda.Mapper.ExameMapper;
import br.com.agenda.Repository.ExameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExameService {

    @Autowired
    private ExameRepository exameRepository;

    public List<ExameDTO> listarTodos() {
        return exameRepository.findAll()
                .stream()
                .map(ExameMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ExameDTO> buscarPorId(Long id) {
        return exameRepository.findById(id)
                .map(ExameMapper::toDTO);
    }

    public ExameDTO salvar(ExameDTO exameDTO) {
        ExameEntity exameEntity = ExameMapper.toEntity(exameDTO);
        ExameEntity exameSalvo = exameRepository.save(exameEntity);
        return ExameMapper.toDTO(exameSalvo);
    }

    public void deletar(Long id) {
        exameRepository.deleteById(id);
    }

    public ExameDTO atualizar(Long id, ExameDTO exameDTO) {
        ExameEntity exameEntity = exameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exame não encontrado com o ID: " + id));
        exameEntity.setNome(exameDTO.getNome());
        exameEntity.setTipo(exameDTO.getTipo());
        ExameEntity exameAtualizado = exameRepository.save(exameEntity);
        return ExameMapper.toDTO(exameAtualizado);
    }
}