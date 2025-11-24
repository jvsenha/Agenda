package br.com.agenda.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Column(nullable = false)
    private String password;
}