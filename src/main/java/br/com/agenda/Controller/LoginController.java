package br.com.agenda.Controller;

import br.com.agenda.DTO.UsuarioRequest;
import br.com.agenda.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota para a página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Simplesmente retorna o nome do arquivo HTML: login.html
    }

    // Rota para EXIBIR o formulário de registro
    @GetMapping("/registrar")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuarioRequest", new UsuarioRequest());
        return "registrar"; // Retorna o arquivo registrar.html
    }

    // Rota para PROCESSAR os dados do formulário de registro
    @PostMapping("/registrar")
    public String registerUser(@ModelAttribute("usuarioRequest") UsuarioRequest usuarioRequest) {
        try {
            // Tenta registrar o novo usuário através do serviço
            usuarioService.registrarNovoUsuario(usuarioRequest.getUsername(), usuarioRequest.getPassword());
            // Se der certo, redireciona para a rota /login com um parâmetro de sucesso
            return "redirect:/login?success";
        } catch (IllegalStateException e) {
            // Se o usuário já existir (lança exceção), redireciona de volta para /registrar com um parâmetro de erro
            return "redirect:/registrar?error";
        }
    }
}