package progweb.locagest.model;

import jakarta.persistence.*;

@Entity
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String modelo;
    private Integer ano;
    private String cor;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    public Veiculo() {
        this.status = StatusVeiculo.DISPONIVEL; // Padrão ao criar
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public StatusVeiculo getStatus() { return status; }
    public void setStatus(StatusVeiculo status) { this.status = status; }
}