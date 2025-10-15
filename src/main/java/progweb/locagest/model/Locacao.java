package progweb.locagest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locacao")
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locacao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Client cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Vehicle veiculo;

    @Column(name = "data_inicial", nullable = false)
    private LocalDateTime dataInicial;

    @Column(name = "data_devolucao", nullable = false)
    private LocalDateTime dataDevolucao;

    @Column(name = "km")
    private Integer km;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getCliente() { return cliente; }
    public void setCliente(Client cliente) { this.cliente = cliente; }

    public Vehicle getVeiculo() { return veiculo; }
    public void setVeiculo(Vehicle veiculo) { this.veiculo = veiculo; }

    public LocalDateTime getDataInicial() { return dataInicial; }
    public void setDataInicial(LocalDateTime dataInicial) { this.dataInicial = dataInicial; }

    public LocalDateTime getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDateTime dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public Integer getKm() { return km; }
    public void setKm(Integer km) { this.km = km; }
}
