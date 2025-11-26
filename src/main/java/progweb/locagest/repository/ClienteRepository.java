package progweb.locagest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progweb.locagest.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}