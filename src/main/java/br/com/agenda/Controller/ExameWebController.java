package br.com.agenda.Controller;

import br.com.agenda.DTO.ExameDTO;
import br.com.agenda.Service.ExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/exames/view")
public class ExameWebController {

    @Autowired
    private ExameService exameService;

    @GetMapping
    public String viewExamesPage(Model model) {
        if (!model.containsAttribute("exameForm")) {
            model.addAttribute("exameForm", new ExameDTO());
        }
        model.addAttribute("listaDeExames", exameService.listarTodos());
        return "exames";
    }

    @PostMapping("/salvar")
    public String saveExame(@ModelAttribute("exameForm") ExameDTO exameDTO, RedirectAttributes redirectAttributes) {
        try {
            if (exameDTO.getId() != null) {
                exameService.atualizar(exameDTO.getId(), exameDTO);
            } else {
                exameService.salvar(exameDTO);
            }
        } catch (DataIntegrityViolationException e) {
            // Captura erro de chave única do banco (Nome duplicado)
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: Já existe um exame cadastrado com este nome.");
        }
        return "redirect:/exames/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        exameService.buscarPorId(id).ifPresent(exame -> {
            redirectAttributes.addFlashAttribute("exameForm", exame);
        });
        return "redirect:/exames/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarExame(@PathVariable Long id) {
        exameService.deletar(id);
        return "redirect:/exames/view";
    }
}