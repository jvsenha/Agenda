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
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            carregarListas(model);
            return "agendamento-exames";
        }
        service.salvar(dto);
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