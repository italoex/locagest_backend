package progweb.locagest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity

public class Locacao {
    @Id
    @Column(name = "id_locacao")
    private Long id;
    @Column(name = "data_inicial")
    private Date dataInicial;
    @Column(name = "data_devolucao")
    private Date dataDevolucao;
    @Column(name = "km")
    private int km;
}
