package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.Veiculo;
import progweb.locagest.service.VeiculoService;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Veiculo> getAll() {
        return service.findAll();
    }

    @GetMapping("/disponiveis")
    public List<Veiculo> getDisponiveis() {
        return service.findDisponiveis();
    }

    @PostMapping
    public ResponseEntity<Veiculo> create(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(service.save(veiculo));
    }
}