package br.com.fiap.monitor_tree_api.repository;

import br.com.fiap.monitor_tree_api.model.Alerta;
import br.com.fiap.monitor_tree_api.model.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByStatusOrderByDataHoraDesc(StatusAlerta status);
}