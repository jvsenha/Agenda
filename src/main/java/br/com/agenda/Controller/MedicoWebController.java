package br.com.agenda.Controller;

import br.com.agenda.DTO.MedicoRequest;
import br.com.agenda.Service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicos/view")
public class MedicoWebController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public String viewMedicosPage(Model model) {
        if (!model.containsAttribute("medicoForm")) {
            model.addAttribute("medicoForm", new MedicoRequest());
        }
        model.addAttribute("listaDeMedicos", medicoService.listarTodos());
        model.addAttribute("agendaCompleta", medicoService.getAgendaCompleta());
        return "medicos";
    }

    @PostMapping("/salvar")
    public String saveMedico(@ModelAttribute("medicoForm") @Valid MedicoRequest medicoRequest,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) { // Adicionar RedirectAttributes

        if (result.hasErrors()) {
            model.addAttribute("listaDeMedicos", medicoService.listarTodos());
            model.addAttribute("agendaCompleta", medicoService.getAgendaCompleta());
            return "medicos";
        }

        try {
            if (medicoRequest.getId() != null) {
                medicoService.atualizar(medicoRequest.getId(), medicoRequest);
            } else {
                medicoService.salvar(medicoRequest);
            }
        } catch (IllegalArgumentException e) {
            // CAPTURA O ERRO DE DUPLICIDADE
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/medicos/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        medicoService.buscarPorId(id).ifPresent(medico -> {
            redirectAttributes.addFlashAttribute("medicoForm", medico);
        });
        return "redirect:/medicos/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarMedico(@PathVariable Long id) {
        medicoService.deletar(id);
        return "redirect:/medicos/view";
    }
}