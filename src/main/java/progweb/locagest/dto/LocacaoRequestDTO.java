package progweb.locagest.dto;

import java.time.LocalDateTime;

public class LocacaoRequestDTO {
    private Long clienteId;
    private Long veiculoId;

    private LocalDateTime dataHoraInicial;
    private LocalDateTime dataHoraPrevistaDevolucao;

    private Integer kmEntrega;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getVeiculoId() { return veiculoId; }
    public void setVeiculoId(Long veiculoId) { this.veiculoId = veiculoId; }

    public LocalDateTime getDataHoraInicial() { return dataHoraInicial; }
    public void setDataHoraInicial(LocalDateTime dataHoraInicial) { this.dataHoraInicial = dataHoraInicial; }

    public LocalDateTime getDataHoraPrevistaDevolucao() { return dataHoraPrevistaDevolucao; }
    public void setDataHoraPrevistaDevolucao(LocalDateTime dataHoraPrevistaDevolucao) { this.dataHoraPrevistaDevolucao = dataHoraPrevistaDevolucao; }

    public Integer getKmEntrega() { return kmEntrega; }
    public void setKmEntrega(Integer kmEntrega) { this.kmEntrega = kmEntrega; }
}