package br.com.fooddelivery.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static int proximoNumero = 1;

    private final int numero;
    private final Cliente cliente;
    private final LocalDateTime dataHora;
    private PedidoStatus status;
    private final List<PedidoItem> itens;

    public Pedido(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Pedido precisa de um cliente.");
        this.cliente = cliente;
        this.numero = proximoNumero++;
        this.dataHora = LocalDateTime.now();
        this.status = PedidoStatus.ACEITO;
        this.itens = new ArrayList<>();
    }

    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getDataHora() { return dataHora; }
    public PedidoStatus getStatus() { return status; }

    public List<PedidoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(PedidoItem novo) {
        if (novo == null) throw new IllegalArgumentException("Item não pode ser nulo.");
        for (int i = 0; i < itens.size(); i++) {
            PedidoItem atual = itens.get(i);
            if (atual.getItem().getCodigo() == novo.getItem().getCodigo()) {
                int novaQtd = atual.getQuantidade() + novo.getQuantidade();
                itens.set(i, new PedidoItem(atual.getItem(), novaQtd));
                return;
            }
        }
        itens.add(novo);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (PedidoItem pi : itens) {
            total = total.add(pi.getSubtotal());
        }
        return total;
    }

    public void avancarStatus() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido sem itens não pode avançar de status.");
        }
        switch (status) {
            case ACEITO -> status = PedidoStatus.PREPARANDO;
            case PREPARANDO -> status = PedidoStatus.FEITO;
            case FEITO -> status = PedidoStatus.AGUARDANDO_ENTREGADOR;
            case AGUARDANDO_ENTREGADOR -> status = PedidoStatus.SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA -> status = PedidoStatus.ENTREGUE;
            case ENTREGUE -> throw new IllegalStateException("Pedido já está ENTREGUE. Não pode avançar.");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Pedido #%d | Cliente: %s | Data: %s | Status: %s | Total: R$ %s",
                numero,
                cliente.getNome(),
                dataHora.format(FMT),
                status,
                calcularTotal().toPlainString()
        );
    }
}