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
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatorio")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo tipo é obrigatório")
    private TipoSensor tipo;

    @NotBlank(message = "O campo localização é obrigatório")
    private String localizacao;

    private LocalDateTime dataCriacao;
}