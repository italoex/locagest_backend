package progweb.locagest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progweb.locagest.model.StatusVeiculo;
import progweb.locagest.model.Veiculo;
import progweb.locagest.repository.VeiculoRepository;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("/disponiveis")
    public List<Veiculo> listarDisponiveis() {
        return veiculoRepository.findByStatus(StatusVeiculo.DISPONIVEL);
    }
}