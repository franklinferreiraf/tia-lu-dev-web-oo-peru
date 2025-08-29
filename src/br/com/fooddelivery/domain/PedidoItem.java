package br.com.fooddelivery.domain;

import java.math.BigDecimal;

public class PedidoItem {

    private final ItemCardapio item;
    private final int quantidade;

    public PedidoItem(ItemCardapio item, int quantidade) {
        if (item == null) throw new IllegalArgumentException("Item do cardápio é obrigatório.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.item = item;
        this.quantidade = quantidade;
    }

    public ItemCardapio getItem() { return item; }
    public int getQuantidade() { return quantidade; }

    public BigDecimal getSubtotal() {
        return item.getPreco().multiply(new BigDecimal(quantidade));
    }

    @Override
    public String toString() {
        return String.format("%dx %s (R$ %s)",
                quantidade, item.getNome(), getSubtotal().toPlainString());
    }
}