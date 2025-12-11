package progweb.locagest.dto;

public class LocacaoCreateDTO {
    private Long idCliente;
    private Long idVeiculo;
    private String dataInicial;
    private String dataDevolucao;
    private Integer km;

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public Long getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(Long idVeiculo) { this.idVeiculo = idVeiculo; }
    public String getDataInicial() { return dataInicial; }
    public void setDataInicial(String dataInicial) { this.dataInicial = dataInicial; }
    public String getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public Integer getKm() { return km; }
    public void setKm(Integer km) { this.km = km; }
}
