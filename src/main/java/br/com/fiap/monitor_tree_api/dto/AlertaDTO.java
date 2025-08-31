package br.com.fiap.monitor_tree_api.dto;

import br.com.fiap.monitor_tree_api.model.Alerta;
import br.com.fiap.monitor_tree_api.model.Sensor;
import br.com.fiap.monitor_tree_api.model.StatusAlerta;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaDTO {
    private Long id;
    private Long sensorId;
    private String descricao;
    private Double valor;
    private Double limite;
    private String tipoAlerta;
    private StatusAlerta status;
    private LocalDateTime dataHora;

    public static AlertaDTO fromEntity(Alerta alerta) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(alerta.getId());
        dto.setSensorId(alerta.getSensor().getId());
        dto.setDescricao(alerta.getDescricao());
        dto.setValor(alerta.getValor());
        dto.setLimite(alerta.getLimite());
        dto.setTipoAlerta(alerta.getTipoAlerta());
        dto.setStatus(alerta.getStatus());
        dto.setDataHora(alerta.getDataHora());
        return dto;
    }

    public Alerta toEntity() {
        Alerta alerta = new Alerta();
        alerta.setId(this.id);
        alerta.setSensor(new Sensor());
        alerta.getSensor().setId(this.sensorId);
        alerta.setDescricao(this.descricao);
        alerta.setValor(this.valor);
        alerta.setLimite(this.limite);
        alerta.setTipoAlerta(this.tipoAlerta);
        alerta.setStatus(this.status);
        alerta.setDataHora(this.dataHora);
        return alerta;
    }
}