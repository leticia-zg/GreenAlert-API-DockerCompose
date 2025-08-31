package br.com.fiap.monitor_tree_api.repository;

import br.com.fiap.monitor_tree_api.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}