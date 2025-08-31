package br.com.fiap.monitor_tree_api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alerta_id", nullable = false)
    @NotNull(message = "O alerta é obrigatório")
    private Alerta alerta;

    @NotNull(message = "O título é obrigatório")
    private String titulo;

    @NotNull(message = "A descrição é obrigatória")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório")
    private ChamadoStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo é obrigatório")
    private ChamadoTipo tipo;

    @NotNull(message = "A data de abertura é obrigatória")
    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;
}
