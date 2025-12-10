package progweb.locagest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.ZonedDateTime;
import progweb.locagest.config.ZonedDateTimeDeserializer;

public class FinalizarLocacaoDTO {
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime dataDevolucao;

    @JsonAlias({ "kmEntrega" })
    private Integer kmFinal;

    public ZonedDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(ZonedDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Integer getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(Integer kmFinal) {
        this.kmFinal = kmFinal;
    }
}
