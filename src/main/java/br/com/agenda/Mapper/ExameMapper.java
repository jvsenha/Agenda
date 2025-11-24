package br.com.agenda.Mapper;

import br.com.agenda.DTO.ExameDTO;
import br.com.agenda.Entity.ExameEntity;

public class ExameMapper {

    public static ExameDTO toDTO(ExameEntity entity) {
        ExameDTO dto = new ExameDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setTipo(entity.getTipo());
        return dto;
    }

    public static ExameEntity toEntity(ExameDTO dto) {
        ExameEntity entity = new ExameEntity();
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        return entity;
    }

    public static ExameEntity toEntity(Long id, ExameDTO dto) {
        ExameEntity entity = toEntity(dto);
        entity.setId(id);
        return entity;
    }
}