package progweb.locagest.service.impl;

import org.springframework.stereotype.Service;
import progweb.locagest.model.Locacao;
import progweb.locagest.repository.LocacaoRepository;
import progweb.locagest.service.LocacaoService;

import java.util.List;

@Service
public class LocacaoServiceImpl implements LocacaoService {

    private final LocacaoRepository repository;

    public LocacaoServiceImpl(LocacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Locacao> findAll() {
        return repository.findAll();
    }

    @Override
    public Locacao getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Locacao create(Locacao locacao) {
        return repository.save(locacao);
    }

    @Override
    public Locacao update(Locacao locacao) {
        return repository.save(locacao);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
