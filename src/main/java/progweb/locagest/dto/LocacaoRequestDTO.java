package progweb.locagest.dto;

import java.time.LocalDateTime;

public class LocacaoRequestDTO {

    private Long veiculoId;
    private Long clienteId;
    private LocalDateTime dataHoraInicial;
    private LocalDateTime dataHoraPrevistaDevolucao;
    private Long kmEntrega;

    // Getters e Setters
    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
}