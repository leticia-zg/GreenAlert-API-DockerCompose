package br.com.fiap.monitor_tree_api.model;

public record Token(
    String token, 
    String type,
    String email
) {}