package progweb.locagest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Veiculo veiculo;

    @ManyToOne
    private Cliente cliente;

    private LocalDateTime dataHoraInicial;
    private LocalDateTime dataHoraPrevistaDevolucao;
    private Long kmEntrega;

    @Enumerated(EnumType.STRING)
    private StatusLocacao status;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHoraInicial() {
        return dataHoraInicial;
    }

    public void setDataHoraInicial(LocalDateTime dataHoraInicial) {
        this.dataHoraInicial = dataHoraInicial;
    }

    public LocalDateTime getDataHoraPrevistaDevolucao() {
        return dataHoraPrevistaDevolucao;
    }

    public void setDataHoraPrevistaDevolucao(LocalDateTime dataHoraPrevistaDevolucao) {
        this.dataHoraPrevistaDevolucao = dataHoraPrevistaDevolucao;
    }

    public Long getKmEntrega() {
        return kmEntrega;
    }

    public void setKmEntrega(Long kmEntrega) {
        this.kmEntrega = kmEntrega;
    }

    public StatusLocacao getStatus() {
        return status;
    }

    public void setStatus(StatusLocacao status) {
        this.status = status;
    }
}