package br.com.agenda.Controller;

import br.com.agenda.DTO.AgendamentoExameDTO;
import br.com.agenda.Service.AgendamentoExameService;
import br.com.agenda.Service.ExameService;
import br.com.agenda.Service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agendamento-exames")
public class AgendamentoExameController {

    @Autowired private AgendamentoExameService service;
    @Autowired private PacienteService pacienteService;
    @Autowired private ExameService exameService;

    @GetMapping
    public String viewPage(Model model) {
        model.addAttribute("agendamentoForm", new AgendamentoExameDTO());
        carregarListas(model);
        return "agendamento-exames";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("agendamentoForm") @Valid AgendamentoExameDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) { // Adicionar RedirectAttributes

        if (result.hasErrors()) {
            // Método auxiliar que você já tinha para carregar as listas
            carregarListas(model);
            return "agendamento-exames";
        }

        try {
            service.salvar(dto);
        } catch (IllegalArgumentException e) {
            // Captura o erro de validação criado no Service
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/agendamento-exames";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/agendamento-exames";
    }

    private void carregarListas(Model model) {
        model.addAttribute("listaAgendamentos", service.listarTodos());
        model.addAttribute("listaPacientes", pacienteService.listarTodos());
        model.addAttribute("listaExames", exameService.listarTodos());
    }
}