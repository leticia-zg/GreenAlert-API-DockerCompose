package br.com.fiap.monitor_tree_api.dto;

import br.com.fiap.monitor_tree_api.model.Alerta;
import br.com.fiap.monitor_tree_api.model.Chamado;
import br.com.fiap.monitor_tree_api.model.ChamadoStatus;
import br.com.fiap.monitor_tree_api.model.ChamadoTipo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChamadoDTO {
    private Long id;
    private Long alertaId;
    private String titulo;
    private String descricao;
    private String status;
    private String tipo;  
    private LocalDateTime dataHoraAbertura;
    private LocalDateTime dataHoraFechamento;

    public Chamado toEntity() {
        Chamado chamado = new Chamado();
        chamado.setId(this.id);
        if (this.alertaId != null) {
            Alerta alerta = new Alerta();
            alerta.setId(this.alertaId);
            chamado.setAlerta(alerta);
        }
        chamado.setTitulo(this.titulo);
        chamado.setDescricao(this.descricao);
        if (this.status != null)
            chamado.setStatus(ChamadoStatus.valueOf(this.status.toUpperCase()));
        if (this.tipo != null)
            chamado.setTipo(ChamadoTipo.valueOf(this.tipo.toUpperCase()));
        chamado.setDataHoraAbertura(this.dataHoraAbertura);
        chamado.setDataHoraFechamento(this.dataHoraFechamento);
        return chamado;
    }

    public static ChamadoDTO fromEntity(Chamado chamado) {
        ChamadoDTO dto = new ChamadoDTO();
        dto.setId(chamado.getId());
        dto.setAlertaId(chamado.getAlerta() != null ? chamado.getAlerta().getId() : null);
        dto.setTitulo(chamado.getTitulo());
        dto.setDescricao(chamado.getDescricao());
        dto.setStatus(chamado.getStatus() != null ? chamado.getStatus().name() : null);
        dto.setTipo(chamado.getTipo() != null ? chamado.getTipo().name() : null);
        dto.setDataHoraAbertura(chamado.getDataHoraAbertura());
        dto.setDataHoraFechamento(chamado.getDataHoraFechamento());
        return dto;
    }
}