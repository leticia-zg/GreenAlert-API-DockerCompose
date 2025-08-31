package br.com.fiap.monitor_tree_api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.monitor_tree_api.model.Usuario;
import br.com.fiap.monitor_tree_api.model.UsuarioFilter;
import jakarta.persistence.criteria.Predicate;

public class UserSpecfication {
    
    public static Specification<Usuario> withFilters(UsuarioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.cpf() != null && !filter.cpf().isBlank()) {
                predicates.add(cb.like(root.get("cpf"), "%" + filter.cpf() + "%"));
            }

            if (filter.telefone() != null && !filter.telefone().isBlank()) {
                predicates.add(cb.like(root.get("telefone"), "%" + filter.telefone() + "%"));
            }

            if (filter.email() != null && !filter.email().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.email().toLowerCase() + "%"));
            }

            if (filter.role() != null) {
                predicates.add(cb.equal(root.get("role"), filter.role()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
