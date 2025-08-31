package br.com.fiap.monitor_tree_api.repository;

import br.com.fiap.monitor_tree_api.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    List<Leitura> findBySensorIdOrderByDataHoraDesc(Long sensorId);
    List<Leitura> findBySensorTipoOrderByDataHoraDesc(String tipo);
    List<Leitura> findByDataHoraBetweenOrderByDataHoraDesc(LocalDateTime inicio, LocalDateTime fim);
    List<Leitura> findBySensorIdAndDataHoraBetweenOrderByDataHoraDesc(Long sensorId, LocalDateTime inicio, LocalDateTime fim);
}