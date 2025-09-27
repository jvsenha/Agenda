package br.com.agenda.Mapper;

import br.com.agenda.DTO.AgendaCompletaDTO;
import br.com.agenda.DTO.MedicoRequest;
import br.com.agenda.DTO.MedicoResponse;
import br.com.agenda.Entity.MedicoEntity;

import java.util.stream.Collectors;

public class MedicoMapper {

    public static MedicoResponse toResponseDTO(MedicoEntity entity) {
        MedicoResponse response = new MedicoResponse();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setEspecialidade(entity.getEspecialidade());
        return response;
    }

    public static MedicoEntity toEntity(MedicoRequest request) {
        MedicoEntity entity = new MedicoEntity();
        entity.setNome(request.getNome());
        entity.setEspecialidade(request.getEspecialidade());
        return entity;
    }

    public static AgendaCompletaDTO toAgendaCompletaDTO(MedicoEntity entity) {
        AgendaCompletaDTO dto = new AgendaCompletaDTO();
        dto.setIdMedico(entity.getId());
        dto.setNomeMedico(entity.getNome());
        dto.setEspecialidade(entity.getEspecialidade());
        dto.setConsultas(entity.getConsultas().stream()
                .map(ConsultaMapper::toResponseDTO) // Reutiliza o mapper de consulta
                .collect(Collectors.toList()));
        return dto;
    }
}