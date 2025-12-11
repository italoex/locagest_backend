package progweb.locagest.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @Column(name = "data_inicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicial;

    @Column(name = "data_devolucao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDevolucao;

    @Column(name = "km_inicial")
    private Integer kmInicial;

    @Column(name = "km_final")
    private Integer kmFinal;

    @Column(name = "km")
    private Integer km;

    @Column(name = "valor")
    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusLocacao status;

    public enum StatusLocacao {
        PENDENTE, ATIVA, FINALIZADA
    }

    public Locacao() {
        this.status = StatusLocacao.PENDENTE;
        this.km = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Integer getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(Integer kmInicial) {
        this.kmInicial = kmInicial;
    }

    public Integer getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(Integer kmFinal) {
        this.kmFinal = kmFinal;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusLocacao getStatus() {
        return status;
    }

    public void setStatus(StatusLocacao status) {
        this.status = status;
    }
}