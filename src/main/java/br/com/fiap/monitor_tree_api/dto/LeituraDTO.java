package br.com.fiap.monitor_tree_api.dto;

import br.com.fiap.monitor_tree_api.model.Leitura;
import br.com.fiap.monitor_tree_api.model.Sensor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeituraDTO {
    private Long id;
    private Long sensorId;
    private Double valor;
    private String unidade;
    private LocalDateTime dataHora;

    public static LeituraDTO fromEntity(Leitura leitura) {
        LeituraDTO dto = new LeituraDTO();
        dto.setId(leitura.getId());
        dto.setSensorId(leitura.getSensor().getId());
        dto.setValor(leitura.getValor());
        dto.setUnidade(leitura.getUnidade());
        dto.setDataHora(leitura.getDataHora());
        return dto;
    }

    public Leitura toEntity() {
        Leitura leitura = new Leitura();
        leitura.setId(this.id);
        leitura.setSensor(new Sensor());
        leitura.getSensor().setId(this.sensorId);
        leitura.setValor(this.valor);
        leitura.setUnidade(this.unidade);
        leitura.setDataHora(this.dataHora);
        return leitura;
    }
}