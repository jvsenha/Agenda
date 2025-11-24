package br.com.agenda.Mapper;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.DTO.ConsultaResponse;
import br.com.agenda.Entity.ConsultaEntity;
import br.com.agenda.Entity.MedicoEntity;
import br.com.agenda.Entity.PacienteEntity;

public class ConsultaMapper {

    public static ConsultaResponse toResponseDTO(ConsultaEntity entity) {
        ConsultaResponse response = new ConsultaResponse();
        response.setId(entity.getId());
        response.setDataConsulta(entity.getDataConsulta());
        response.setObservacoes(entity.getObservacoes());
        if (entity.getPaciente() != null) {
            response.setPaciente(entity.getPaciente().getNome());
        }
        response.setObservacoes(entity.getObservacoes());

        if (entity.getMedico() != null) {
            response.setMedicoNome(entity.getMedico().getNome());
            response.setMedicoId(entity.getMedico().getId());
        }
        return response;
    }

    public static ConsultaEntity toEntity(ConsultaRequest request, MedicoEntity medico, PacienteEntity paciente) {
        ConsultaEntity entity = new ConsultaEntity();
        if (request.getId() != null) {
            entity.setId(request.getId());
        }
        entity.setDataConsulta(request.getDataConsulta());
        entity.setPaciente(paciente); // Define o paciente real
        entity.setObservacoes(request.getObservacoes());
        entity.setMedico(medico);
        return entity;
    }
}