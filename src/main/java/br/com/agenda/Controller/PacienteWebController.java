package br.com.agenda.Controller;

import br.com.agenda.DTO.PacienteDTO;
import br.com.agenda.Service.ExameService; // Import necessário
import br.com.agenda.Service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pacientes/view")
public class PacienteWebController {

    @Autowired
    private PacienteService pacienteService;

    // NOVO: Injeção do ExameService para listar as opções
    @Autowired
    private ExameService exameService;

    @GetMapping
    public String viewPacientesPage(Model model) {
        if (!model.containsAttribute("pacienteForm")) {
            model.addAttribute("pacienteForm", new PacienteDTO());
        }
        model.addAttribute("listaDePacientes", pacienteService.listarTodos());
        return "pacientes";
    }

    @PostMapping("/salvar")
    public String savePaciente(@ModelAttribute("pacienteForm") @Valid PacienteDTO pacienteDTO,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("listaDePacientes", pacienteService.listarTodos());
            return "pacientes";
        }

        try {
            if (pacienteDTO.getId() != null) {
                pacienteService.atualizar(pacienteDTO.getId(), pacienteDTO);
            } else {
                pacienteService.salvar(pacienteDTO);
            }
        } catch (DataIntegrityViolationException e) {
            // Captura erro de duplicidade (CPF ou Email)
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: CPF ou Email já cadastrado no sistema.");
        }

        return "redirect:/pacientes/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pacienteService.buscarPorId(id).ifPresent(paciente -> {
            redirectAttributes.addFlashAttribute("pacienteForm", paciente);
        });
        return "redirect:/pacientes/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarPaciente(@PathVariable Long id) {
        pacienteService.deletar(id);
        return "redirect:/pacientes/view";
    }

    // --- NOVAS ROTAS PARA VINCULAR EXAMES ---

    @GetMapping("/{id}/exames")
    public String gerenciarExames(@PathVariable Long id, Model model) {
        // Busca o paciente para exibir o nome no título
        pacienteService.buscarPorId(id).ifPresent(paciente -> {
            model.addAttribute("paciente", paciente);
            // Busca quais exames já estão marcados para este paciente
            model.addAttribute("examesSelecionados", pacienteService.buscarIdsExamesDoPaciente(id));
        });

        // Lista todos os exames disponíveis para criar os checkboxes
        model.addAttribute("todosExames", exameService.listarTodos());

        return "paciente-exames"; // Nome do novo arquivo HTML
    }

    @PostMapping("/{id}/exames")
    public String salvarExamesDoPaciente(@PathVariable Long id,
                                         @RequestParam(name = "exames", required = false) List<Long> examesIds) {
        pacienteService.atualizarExamesDoPaciente(id, examesIds);
        return "redirect:/pacientes/view";
    }
}