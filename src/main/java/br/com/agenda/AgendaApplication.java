package br.com.agenda;

import br.com.agenda.Entity.UsuarioEntity;
import br.com.agenda.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (usuarioRepository.findByUsername("admin").isEmpty()) {
				UsuarioEntity admin = new UsuarioEntity();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("password"));
				usuarioRepository.save(admin);
			}
		};
	}

}
