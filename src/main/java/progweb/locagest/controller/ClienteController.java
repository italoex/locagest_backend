package progweb.locagest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.Cliente;
import progweb.locagest.repository.ClienteRepository;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}