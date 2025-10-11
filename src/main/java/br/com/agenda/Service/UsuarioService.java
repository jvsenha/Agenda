package br.com.agenda.Service;

import br.com.agenda.Entity.UsuarioEntity;
import br.com.agenda.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioEntity registrarNovoUsuario(String username, String password) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Nome de usuário já existe: " + username);
        }

        UsuarioEntity novoUsuario = new UsuarioEntity();
        novoUsuario.setUsername(username);
        novoUsuario.setPassword(passwordEncoder.encode(password));

        return usuarioRepository.save(novoUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}