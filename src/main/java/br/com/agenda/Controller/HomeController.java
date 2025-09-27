package br.com.agenda.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Responderá pela URL raiz, como http://localhost:8080/
    public String home() {
        return "home"; // Retorna o nome do arquivo: home.html
    }
}