package br.com.agenda.Controller;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.Service.ConsultaService;
import br.com.agenda.Service.MedicoService;
import br.com.agenda.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultas/view")
public class ConsultaWebController {

    @Autowired private ConsultaService consultaService;
    @Autowired private MedicoService medicoService;
    @Autowired private PacienteService pacienteService; // Necessário para preencher o select

    @GetMapping
    public String viewConsultasPage(Model model) {
        if (!model.containsAttribute("consultaForm")) {
            model.addAttribute("consultaForm", new ConsultaRequest());
        }
        model.addAttribute("listaDeConsultas", consultaService.listarTodas());
        model.addAttribute("listaDeMedicos", medicoService.listarTodos());
        // CORREÇÃO: Envia a lista de pacientes para o dropdown
        model.addAttribute("listaDePacientes", pacienteService.listarTodos());
        return "consultas";
    }

    @PostMapping("/salvar")
    public String saveConsulta(@ModelAttribute("consultaForm") ConsultaRequest consultaRequest,
                               RedirectAttributes redirectAttributes) {
        try {
            if (consultaRequest.getId() != null) {
                consultaService.atualizar(consultaRequest.getId(), consultaRequest);
            } else {
                consultaService.salvar(consultaRequest);
            }
        } catch (IllegalArgumentException e) {
            // CORREÇÃO: Captura erro de validação (horário ocupado) e exibe mensagem
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Mantém os dados preenchidos em caso de erro (opcional, requer mais lógica de DTO)
        }
        return "redirect:/consultas/view";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        consultaService.buscarPorId(id).ifPresent(consulta -> {
            // Nota: Será necessário adaptar o ConsultaRequest para ter o ID do paciente vindo do Response
            // Para simplificar aqui, assumimos que o mapper já cuida disso ou ajustamos manualmente:
            ConsultaRequest form = new ConsultaRequest();
            form.setId(consulta.getId());
            form.setDataConsulta(consulta.getDataConsulta());
            form.setMedicoId(consulta.getMedicoId());
            form.setObservacoes(consulta.getObservacoes());
            // Aqui precisariamos que o ConsultaResponse tivesse o ID do paciente também,
            // mas o código original não tinha. Assumindo a correção no Mapper/DTO Response.
            redirectAttributes.addFlashAttribute("consultaForm", form);
        });
        return "redirect:/consultas/view";
    }

    @GetMapping("/deletar/{id}")
    public String deletarConsulta(@PathVariable Long id) {
        consultaService.deletar(id);
        return "redirect:/consultas/view";
    }
}