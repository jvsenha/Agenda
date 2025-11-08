package br.com.agenda.Controller;

import br.com.agenda.DTO.PacienteDTO;
import br.com.agenda.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes/view")
public class PacienteWebController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String viewPacientesPage(Model model) {
        if (!model.containsAttribute("pacienteForm")) {
            model.addAttribute("pacienteForm", new PacienteDTO());
        }
        model.addAttribute("listaDePacientes", pacienteService.listarTodos());
        return "pacientes";
    }

    @PostMapping("/salvar")
    public String savePaciente(@ModelAttribute("pacienteForm") PacienteDTO pacienteDTO) {
        if (pacienteDTO.getId() != null) {
            pacienteService.atualizar(pacienteDTO.getId(), pacienteDTO);
        } else {
            pacienteService.salvar(pacienteDTO);
        }
        return "redirect:/pacientes/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pacienteService.buscarPorId(id).ifPresent(paciente -> {
            PacienteDTO form = new PacienteDTO();
            form.setId(paciente.getId());
            form.setNome(paciente.getNome());
            form.setCpf(paciente.getCpf());
            redirectAttributes.addFlashAttribute("pacienteForm", form);
        });
        return "redirect:/pacientes/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarPaciente(@PathVariable Long id) {
        pacienteService.deletar(id);
        return "redirect:/pacientes/view";
    }
}