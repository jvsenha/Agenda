package br.com.agenda.Mapper;

import br.com.agenda.DTO.PacienteDTO;
import br.com.agenda.Entity.PacienteEntity;

public class PacienteMapper {

    public static PacienteDTO toDTO(PacienteEntity entity) {
        PacienteDTO response = new PacienteDTO();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setCpf(entity.getCpf());
        response.setTelefone(entity.getTelefone());
        response.setEmail(entity.getEmail());
        return response;
    }

    public static PacienteEntity toEntity(PacienteDTO request) {
        PacienteEntity entity = new PacienteEntity();
        entity.setNome(request.getNome());
        entity.setCpf(request.getCpf());
        entity.setTelefone(request.getTelefone());
        entity.setEmail(request.getEmail());
        return entity;
    }
}