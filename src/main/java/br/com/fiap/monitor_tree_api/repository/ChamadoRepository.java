package br.com.fiap.monitor_tree_api.repository;

import br.com.fiap.monitor_tree_api.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}