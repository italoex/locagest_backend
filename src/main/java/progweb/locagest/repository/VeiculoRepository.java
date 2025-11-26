package progweb.locagest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progweb.locagest.model.StatusVeiculo;
import progweb.locagest.model.Veiculo;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByStatus(StatusVeiculo status);
}