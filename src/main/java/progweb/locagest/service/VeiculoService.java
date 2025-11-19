package progweb.locagest.service;

import org.springframework.stereotype.Service;
import progweb.locagest.model.StatusVeiculo;
import progweb.locagest.model.Veiculo;
import progweb.locagest.repository.VeiculoRepository;

import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> findAll() {
        return repository.findAll();
    }

    public List<Veiculo> findDisponiveis() {
        return repository.findByStatus(StatusVeiculo.DISPONIVEL);
    }

    public Veiculo save(Veiculo veiculo) {
        // Lógica simples: se for novo, garante status DISPONIVEL
        if (veiculo.getId() == null) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        }
        return repository.save(veiculo);
    }
}