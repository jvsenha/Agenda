package br.com.agenda.Controller;

import br.com.agenda.DTO.ConsultaRequest;
import br.com.agenda.DTO.ConsultaResponse;
import br.com.agenda.Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ConsultaResponse> criarConsulta(@RequestBody ConsultaRequest consultaRequest) {
        ConsultaResponse consultaCriada = consultaService.salvar(consultaRequest);
        return new ResponseEntity<>(consultaCriada, HttpStatus.CREATED);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ConsultaResponse> atualizarConsulta(
            @PathVariable Long id,
            @RequestBody ConsultaRequest consultaRequest) {

        ConsultaResponse consultaAtualizada = consultaService.atualizar(id, consultaRequest);
        return ResponseEntity.ok(consultaAtualizada);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConsultaResponse>> listarTodasConsultas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarConsultaPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}