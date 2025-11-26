package progweb.locagest.service;

import org.springframework.stereotype.Service;
import progweb.locagest.model.Cliente;
import progweb.locagest.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }
}