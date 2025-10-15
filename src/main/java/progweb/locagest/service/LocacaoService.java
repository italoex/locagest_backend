package progweb.locagest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import progweb.locagest.dto.LocacaoCreateDTO;
import progweb.locagest.model.*;
import progweb.locagest.repository.ClientRepository;
import progweb.locagest.repository.LocacaoRepository;
import progweb.locagest.repository.VehicleRepository;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    public LocacaoService(LocacaoRepository locacaoRepository, ClientRepository clientRepository, VehicleRepository vehicleRepository) {
        this.locacaoRepository = locacaoRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Locacao> findAll() {
        return locacaoRepository.findAll();
    }

    @Transactional
    public Locacao create(LocacaoCreateDTO dto) {
        if (dto.getIdCliente() == null || dto.getIdVeiculo() == null || dto.getDataInicial() == null || dto.getDataDevolucao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios ausentes.");
        }

        Client client = clientRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado."));
        Vehicle vehicle = vehicleRepository.findById(dto.getIdVeiculo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado."));

        LocalDateTime inicio;
        LocalDateTime devolucao;
        try {
            inicio = LocalDateTime.parse(dto.getDataInicial());
            devolucao = LocalDateTime.parse(dto.getDataDevolucao());
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido. Use ISO local datetime (ex: 2025-10-10T09:00).");
        }

        if (inicio.isAfter(devolucao) || inicio.isEqual(devolucao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data inicial deve ser anterior à data prevista de devolução.");
        }

        if (vehicle.getStatus() == VehicleStatus.LOCADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo já está locado.");
        }

        if (client.getCnhValidade() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNH não cadastrada para este cliente.");
        }
        LocalDate cnhValidade = client.getCnhValidade();
        LocalDate inicioDate = inicio.toLocalDate();
        if (cnhValidade.isBefore(inicioDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNH vencida antes da data de início da locação.");
        }

        Locacao locacao = new Locacao();
        locacao.setCliente(client);
        locacao.setVeiculo(vehicle);
        locacao.setDataInicial(inicio);
        locacao.setDataDevolucao(devolucao);
        locacao.setKm(dto.getKm());

        Locacao saved = locacaoRepository.save(locacao);

        vehicle.setStatus(VehicleStatus.LOCADO);
        if (dto.getKm() != null) {
            vehicle.setKmAtual(dto.getKm());
        }
        vehicleRepository.save(vehicle);

        return saved;
    }
}
