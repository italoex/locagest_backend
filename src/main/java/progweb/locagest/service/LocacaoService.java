package progweb.locagest.service;

import progweb.locagest.model.Locacao;

import java.util.List;

public interface LocacaoService {
    List<Locacao> findAll();

    Locacao getById(Long id);

    Locacao create(Locacao locacao);

    Locacao update(Locacao locacao);

    void delete(Long id);
}
