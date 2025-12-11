package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.Locacao;
import progweb.locagest.service.LocacaoService;
import progweb.locagest.dto.LocacaoRequestDTO;
import progweb.locagest.dto.FinalizarLocacaoDTO;
import java.util.Date;
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

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> getById(@PathVariable Long id) {
        Locacao locacao = service.findById(id);
        return ResponseEntity.ok(locacao);
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

    @PutMapping("/{id}/end")
    public ResponseEntity<Locacao> endLocacao(@PathVariable Long id, @RequestBody FinalizarLocacaoDTO dto) {
        // Converter ZonedDateTime para java.util.Date
        Date dataDevolucao = null;
        if (dto.getDataDevolucao() != null) {
            dataDevolucao = Date.from(dto.getDataDevolucao().toInstant());
        }
        Integer kmFinal = dto.getKmFinal();
        Locacao locacao = service.finalizarLocacao(id, dataDevolucao, kmFinal);
        return ResponseEntity.ok(locacao);
    }
}