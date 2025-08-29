package br.com.fooddelivery.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ItemCardapio {

    private static int proximoCodigo = 1;

    private final int codigo;
    private String nome;
    private BigDecimal preco;

    public ItemCardapio(String nome, BigDecimal preco) {
        setNome(nome);
        setPreco(preco);
        this.codigo = proximoCodigo++;
    }

    public int getCodigo() { return codigo; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do item é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public BigDecimal getPreco() { return preco; }

    public void setPreco(BigDecimal preco) {
        if (preco == null) throw new IllegalArgumentException("Preço é obrigatório.");
        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.preco = preco.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return String.format("Item #%d: %s - R$ %s",
                codigo, nome, preco.toPlainString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCardapio i)) return false;
        return codigo == i.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}