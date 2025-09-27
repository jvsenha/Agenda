package br.com.agenda.Mapper;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.DTO.ConsultaResponse;
import br.com.agenda.Entity.ConsultaEntity;
import br.com.agenda.Entity.MedicoEntity;

public class ConsultaMapper {

    public static ConsultaResponse toResponseDTO(ConsultaEntity entity) {
        ConsultaResponse response = new ConsultaResponse();
        response.setId(entity.getId());
        response.setDataConsulta(entity.getDataConsulta());
        response.setPaciente(entity.getPaciente());
        response.setObservacoes(entity.getObservacoes());
        return response;
    }

    public static ConsultaEntity toEntity(ConsultaRequest request, MedicoEntity medico) {
        ConsultaEntity entity = new ConsultaEntity();
        entity.setDataConsulta(request.getDataConsulta());
        entity.setPaciente(request.getPaciente());
        entity.setObservacoes(request.getObservacoes());
        entity.setMedico(medico);
        return entity;
    }
}