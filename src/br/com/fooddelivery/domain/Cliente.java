package br.com.fooddelivery.domain;

import java.util.Objects;

public class Cliente {

    private static int proximoId = 1;

    private final int id;
    private String nome;
    private String telefone;

    public Cliente(String nome, String telefone) {
        setNome(nome);
        setTelefone(telefone);
        this.id = proximoId++;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone do cliente é obrigatório.");
        }
        this.telefone = telefone.trim();
    }

    @Override
    public String toString() {
        return String.format("Cliente #%d: %s (%s)", id, nome, telefone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente c)) return false;
        return id == c.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
