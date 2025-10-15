package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.dto.LocacaoCreateDTO;
import progweb.locagest.model.Locacao;
import progweb.locagest.service.LocacaoService;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
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
    public ResponseEntity<Locacao> create(@RequestBody LocacaoCreateDTO dto) {
        Locacao created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }
}
