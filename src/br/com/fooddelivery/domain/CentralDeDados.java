package br.com.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentralDeDados {

    private static final CentralDeDados INSTANCE = new CentralDeDados();
    public static CentralDeDados getInstance() { return INSTANCE; }
    private CentralDeDados() { }

    private final List<Cliente> clientes = new ArrayList<>();
    private final List<ItemCardapio> itens   = new ArrayList<>();
    private final List<Pedido> pedidos       = new ArrayList<>();

    // Clientes
    public void adicionarCliente(Cliente c) {
        if (c == null) throw new IllegalArgumentException("Cliente nulo.");
        clientes.add(c);
    }
    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(clientes);
    }
    public Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) if (c.getId() == id) return c;
        return null;
    }

    // Itens
    public void adicionarItem(ItemCardapio i) {
        if (i == null) throw new IllegalArgumentException("Item nulo.");
        itens.add(i);
    }
    public List<ItemCardapio> listarItens() {
        return Collections.unmodifiableList(itens);
    }
    public ItemCardapio buscarItemPorCodigo(int codigo) {
        for (ItemCardapio i : itens) if (i.getCodigo() == codigo) return i;
        return null;
    }

    // Pedidos
    public void adicionarPedido(Pedido p) {
        if (p == null) throw new IllegalArgumentException("Pedido nulo.");
        pedidos.add(p);
    }
    public List<Pedido> listarPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
    public List<Pedido> listarPedidosPorStatus(PedidoStatus status) {
        List<Pedido> filtro = new ArrayList<>();
        for (Pedido p : pedidos) if (p.getStatus() == status) filtro.add(p);
        return Collections.unmodifiableList(filtro);
    }
    public Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido p : pedidos) if (p.getNumero() == numero) return p;
        return null;
    }

    // Relatórios
    public int quantidadeTotalDePedidos() {
        return pedidos.size();
    }
    public BigDecimal valorTotalArrecadado() {
        BigDecimal total = BigDecimal.ZERO;
        for (Pedido p : pedidos) total = total.add(p.calcularTotal());
        return total;
    }
}

