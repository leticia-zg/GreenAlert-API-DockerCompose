package br.com.fiap.monitor_tree_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    private Double valor;

    @NotNull(message = "O limite é obrigatório")
    private Double limite;

    @NotBlank(message = "O tipo de alerta é obrigatório")
    private String tipoAlerta;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório")
    private StatusAlerta status;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
}