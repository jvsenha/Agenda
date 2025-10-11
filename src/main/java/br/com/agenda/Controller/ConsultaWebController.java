package br.com.agenda.Controller;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.Service.ConsultaService;
import br.com.agenda.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultas/view")
public class ConsultaWebController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public String viewConsultasPage(Model model) {
        if (!model.containsAttribute("consultaForm")) {
            model.addAttribute("consultaForm", new ConsultaRequest());
        }
        model.addAttribute("listaDeConsultas", consultaService.listarTodas());
        model.addAttribute("listaDeMedicos", medicoService.listarTodos());
        return "consultas";
    }

    @PostMapping("/salvar")
    public String saveConsulta(@ModelAttribute("consultaForm") ConsultaRequest consultaRequest) {
        if (consultaRequest.getId() != null) {
            consultaService.atualizar(consultaRequest.getId(), consultaRequest);
        } else {
            consultaService.salvar(consultaRequest);
        }
        return "redirect:/consultas/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        consultaService.buscarPorId(id).ifPresent(consulta -> {
            redirectAttributes.addFlashAttribute("consultaForm", consulta);
        });
        return "redirect:/consultas/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarConsulta(@PathVariable Long id) {
        consultaService.deletar(id);
        return "redirect:/consultas/view";
    }
}