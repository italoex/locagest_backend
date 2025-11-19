package progweb.locagest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progweb.locagest.dto.LocacaoRequestDTO;
import progweb.locagest.model.Cliente;
import progweb.locagest.model.Locacao;
import progweb.locagest.model.StatusLocacao;
import progweb.locagest.model.StatusVeiculo;
import progweb.locagest.model.Veiculo;
import progweb.locagest.repository.ClienteRepository;
import progweb.locagest.repository.LocacaoRepository;
import progweb.locagest.repository.VeiculoRepository;

import java.time.LocalDate;

@Service
public class LocacaoService { // <-- Certifique-se que é uma 'class'

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    // @Transactional garante que ou tudo (salvar locação E alterar status do veículo)
    // funciona, ou nada é salvo no banco se der erro.
    @Transactional
    public Locacao iniciarLocacao(LocacaoRequestDTO dto) {

        // 1. Buscar entidades
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new LocacaoException("Cliente não encontrado."));

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new LocacaoException("Veículo não encontrado."));

        // 2. REQUISITO: Validar CNH
        // Esta linha agora vai funcionar porque você adicionou o campo no Cliente.java
        if (cliente.getDataValidadeCnh().isBefore(LocalDate.now())) {
            throw new LocacaoException("Não é possível locar: CNH do cliente está vencida.");
        }

        // 3. Validação extra: Verificar se o veículo está DISPONÍVEL
        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new LocacaoException("Veículo selecionado não está disponível para locação.");
        }

        // 4. REQUISITO: Marcar como locado
        veiculo.setStatus(StatusVeiculo.LOCADO);
        veiculoRepository.save(veiculo); // Atualiza o status do veículo no banco

        // 5. Criar a nova locação
        Locacao novaLocacao = new Locacao();
        novaLocacao.setCliente(cliente);
        novaLocacao.setVeiculo(veiculo);
        novaLocacao.setDataHoraInicial(dto.getDataHoraInicial());
        novaLocacao.setDataHoraPrevistaDevolucao(dto.getDataHoraPrevistaDevolucao());
        novaLocacao.setKmEntrega(dto.getKmEntrega());
        novaLocacao.setStatus(StatusLocacao.ATIVA); // Inicia como ATIVA

        return locacaoRepository.save(novaLocacao);
    }
}