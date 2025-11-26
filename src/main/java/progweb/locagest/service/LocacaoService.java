package progweb.locagest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.List;
import java.time.ZoneId;

import progweb.locagest.dto.LocacaoRequestDTO;
import progweb.locagest.model.Cliente;
import progweb.locagest.model.Locacao;
import progweb.locagest.model.Veiculo;
import progweb.locagest.model.StatusVeiculo;
import progweb.locagest.repository.ClienteRepository;
import progweb.locagest.repository.LocacaoRepository;
import progweb.locagest.repository.VeiculoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Locacao> findAll() {
        return locacaoRepository.findAll();
    }

    @Transactional
    public Locacao iniciarLocacao(LocacaoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado."));

        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Veículo selecionado não está disponível.");
        }
        veiculo.setStatus(StatusVeiculo.LOCADO);
        veiculoRepository.save(veiculo);

        Locacao novaLocacao = new Locacao();
        novaLocacao.setCliente(cliente);
        novaLocacao.setVeiculo(veiculo);

        if (dto.getDataHoraInicial() != null) {
            Date dataIni = Date.from(dto.getDataHoraInicial().atZone(ZoneId.systemDefault()).toInstant());
            novaLocacao.setDataInicial(dataIni);
        } else {
            novaLocacao.setDataInicial(new Date());
        }

        if (dto.getDataHoraPrevistaDevolucao() != null) {
            Date dataFim = Date.from(dto.getDataHoraPrevistaDevolucao().atZone(ZoneId.systemDefault()).toInstant());
            novaLocacao.setDataDevolucao(dataFim);
        }

        novaLocacao.setKm(dto.getKmEntrega());
        novaLocacao.setStatus(Locacao.StatusLocacao.ATIVA);

        return locacaoRepository.save(novaLocacao);
    }

    @Transactional
    public Locacao confirmarInicioLocacao(Long id) {
        Locacao locacao = locacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada"));

        if (locacao.getStatus() == Locacao.StatusLocacao.PENDENTE) {
            locacao.setStatus(Locacao.StatusLocacao.ATIVA);
            locacao.setDataInicial(new Date());
        }

        return locacaoRepository.save(locacao);
    }
}