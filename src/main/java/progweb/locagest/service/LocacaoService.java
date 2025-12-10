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

    public Locacao findById(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada"));
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

        novaLocacao.setKmInicial(dto.getKmEntrega());
        novaLocacao.setStatus(Locacao.StatusLocacao.ATIVA);

        // Calcular valor estimado
        Double valorEstimado = calcularValorEstimado(novaLocacao, veiculo);
        novaLocacao.setValor(valorEstimado);

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

    @Transactional
    public Locacao finalizarLocacao(Long id, Date dataDevolucao, Integer kmFinal) {
        Locacao locacao = locacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada"));
        if (locacao.getStatus() != Locacao.StatusLocacao.ATIVA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Locação não está ativa.");
        }

        // Definir o kmFinal e data de devolução real
        locacao.setKmFinal(kmFinal);
        locacao.setDataDevolucao(dataDevolucao);

        // Calcular km percorridos ANTES de calcular o valor final
        if (locacao.getKmInicial() != null && kmFinal != null) {
            locacao.setKm(kmFinal - locacao.getKmInicial());
        }

        // Recalcular valor com dados reais (data e km reais)
        Veiculo veiculo = locacao.getVeiculo();
        Double valorFinal = calcularValorFinal(locacao, veiculo);
        locacao.setValor(valorFinal);

        locacao.setStatus(Locacao.StatusLocacao.FINALIZADA);

        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        veiculoRepository.save(veiculo);

        return locacaoRepository.save(locacao);
    }

    // Calcula o valor estimado da locação com base nos dias estimados e km
    // estimados
    private Double calcularValorEstimado(Locacao locacao, Veiculo veiculo) {
        if (locacao.getDataInicial() == null || locacao.getDataDevolucao() == null) {
            return 0.0;
        }

        // Calcular dias estimados
        long diferencaMs = locacao.getDataDevolucao().getTime() - locacao.getDataInicial().getTime();
        long diasEstimados = Math.max(1, diferencaMs / (1000 * 60 * 60 * 24));

        // Obter preços base por categoria
        Double precoDiaria = getPrecoDiariaPorCategoria(veiculo.getCategoria());
        Double precoKm = getPrecoKmPorCategoria(veiculo.getCategoria());

        // Estimar km (se não houver dados, usar média de 100km/dia)
        long kmEstimados = diasEstimados * 100;

        // Calcular valor total estimado
        Double valorDiarias = diasEstimados * precoDiaria;
        Double valorKm = kmEstimados * precoKm;

        return valorDiarias + valorKm;
    }

    // Calcula o valor final da locação com base nos dias e km reais
    private Double calcularValorFinal(Locacao locacao, Veiculo veiculo) {
        if (locacao.getDataInicial() == null || locacao.getDataDevolucao() == null) {
            return locacao.getValor() != null ? locacao.getValor() : 0.0;
        }

        // Calcular dias reais
        long diferencaMs = locacao.getDataDevolucao().getTime() - locacao.getDataInicial().getTime();
        long diasReais = Math.max(1, diferencaMs / (1000 * 60 * 60 * 24));

        // Obter preços base por categoria
        Double precoDiaria = getPrecoDiariaPorCategoria(veiculo.getCategoria());
        Double precoKm = getPrecoKmPorCategoria(veiculo.getCategoria());

        // Usar km reais percorridos
        Integer kmReais = locacao.getKm() != null ? locacao.getKm() : 0;

        // Calcular valor total real
        Double valorDiarias = diasReais * precoDiaria;
        Double valorKm = kmReais * precoKm;

        return valorDiarias + valorKm;
    }

    // Retorna o preço da diária por categoria de veículo
    private Double getPrecoDiariaPorCategoria(String categoria) {
        if (categoria == null)
            return 100.0;

        return switch (categoria.toUpperCase()) {
            case "ECONOMICO" -> 80.0;
            case "INTERMEDIARIO" -> 120.0;
            case "EXECUTIVO" -> 200.0;
            case "SUV" -> 250.0;
            case "LUXO" -> 400.0;
            default -> 100.0;
        };
    }

    // Retorna o preço por km por categoria de veículo
    private Double getPrecoKmPorCategoria(String categoria) {
        if (categoria == null)
            return 0.5;

        return switch (categoria.toUpperCase()) {
            case "ECONOMICO" -> 0.30;
            case "INTERMEDIARIO" -> 0.50;
            case "EXECUTIVO" -> 0.80;
            case "SUV" -> 1.00;
            case "LUXO" -> 1.50;
            default -> 0.50;
        };
    }
}