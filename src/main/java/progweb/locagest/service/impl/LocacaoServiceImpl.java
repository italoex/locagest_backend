// package progweb.locagest.service.impl;

// import org.springframework.stereotype.Service;
// import progweb.locagest.model.Locacao;
// import progweb.locagest.repository.LocacaoRepository;
// import progweb.locagest.service.LocacaoService;

// import java.util.List;

// @Service
// public class LocacaoServiceImpl extends LocacaoService {
// private final LocacaoRepository repository;

// public LocacaoServiceImpl(LocacaoRepository repository) {
// this.repository = repository;
// }

// public List<Locacao> findAll() {
// return repository.findAll();
// }

// public Locacao getById(Long id) {
// return repository.findById(id).orElse(null);
// }

// public Locacao create(Locacao locacao) {
// return repository.save(locacao);
// }

// public Locacao update(Locacao locacao) {
// return repository.save(locacao);
// }

// public void delete(Long id) {
// repository.deleteById(id);
// }
// }
