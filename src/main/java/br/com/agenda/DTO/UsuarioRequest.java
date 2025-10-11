package br.com.agenda.DTO;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String username;
    private String password;
}