package br.com.fiap.monitor_tree_api.model;

public record UsuarioFilter(
    String nome,
    String cpf,
    String telefone,
    String email,
    UserRole role
) {}