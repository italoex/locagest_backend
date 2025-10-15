package progweb.locagest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progweb.locagest.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
