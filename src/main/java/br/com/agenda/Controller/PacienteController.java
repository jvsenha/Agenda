package br.com.agenda.Controller;

import br.com.agenda.DTO.PacienteDTO;
import br.com.agenda.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<PacienteDTO> criarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO pacienteCriado = pacienteService.salvar(pacienteDTO);
        return new ResponseEntity<>(pacienteCriado, HttpStatus.CREATED);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(
            @PathVariable Long id,
            @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO pacienteAtualizado = pacienteService.atualizar(id, pacienteDTO);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteDTO>> listarTodosPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPacientePorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}