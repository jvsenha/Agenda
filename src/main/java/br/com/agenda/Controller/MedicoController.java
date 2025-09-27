package br.com.agenda.Controller;

import br.com.agenda.DTO.AgendaCompletaDTO;
import br.com.agenda.DTO.MedicoRequest;
import br.com.agenda.DTO.MedicoResponse;
import br.com.agenda.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<MedicoResponse> criarMedico(@RequestBody MedicoRequest medicoRequest) {
        MedicoResponse medicoCriado = medicoService.salvar(medicoRequest);
        return new ResponseEntity<>(medicoCriado, HttpStatus.CREATED);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<MedicoResponse> atualizarMedico(
            @PathVariable Long id,
            @RequestBody MedicoRequest medicoRequest) {
        MedicoResponse medicoAtualizado = medicoService.atualizar(id, medicoRequest);
        return ResponseEntity.ok(medicoAtualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MedicoResponse>> listarTodosMedicos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponse> buscarMedicoPorId(@PathVariable Long id) {
        return medicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/agenda-completa")
    public ResponseEntity<List<AgendaCompletaDTO>> getAgendaCompleta() {
        return ResponseEntity.ok(medicoService.getAgendaCompleta());
    }
}