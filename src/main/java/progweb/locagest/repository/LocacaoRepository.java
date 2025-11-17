package progweb.locagest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progweb.locagest.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
}
