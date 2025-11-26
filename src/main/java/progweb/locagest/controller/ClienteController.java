package progweb.locagest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.Cliente;
import progweb.locagest.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.save(cliente));
    }
}