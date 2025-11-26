package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.Locacao;
import progweb.locagest.service.LocacaoService;
import progweb.locagest.dto.LocacaoRequestDTO; // Importe o DTO

import java.util.List;

@RestController
@RequestMapping("/api/locacoes")
public class LocacaoController {

    private final LocacaoService service;

    public LocacaoController(LocacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Locacao> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Locacao> create(@RequestBody LocacaoRequestDTO dto) {
        Locacao novaLocacao = service.iniciarLocacao(dto);
        return ResponseEntity.ok(novaLocacao);
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<Locacao> startLocacao(@PathVariable Long id) {
        Locacao locacao = service.confirmarInicioLocacao(id);
        return ResponseEntity.ok(locacao);
    }
}