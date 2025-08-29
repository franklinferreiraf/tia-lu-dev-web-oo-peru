package br.com.fooddelivery.ui;

import br.com.fooddelivery.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuCLI {

    private final Scanner in = new Scanner(System.in).useLocale(Locale.US);
    private final CentralDeDados db = CentralDeDados.getInstance();

    public void iniciar() {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> cadastrarItem();
                case 4 -> listarItens();
                case 5 -> criarPedido();
                case 6 -> avancarStatusPedido();
                case 7 -> listarPedidosPorStatus();
                case 8 -> relatorioSimplificado();
                case 9 -> relatorioDetalhado();
                case 0 -> System.out.println("Saindo... Valeu!");
                default -> System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (opcao != 0);
    }

    private void mostrarMenu() {
        System.out.println("======== FOOD DELIVERY ========");
        System.out.println("1) Cadastrar Cliente");
        System.out.println("2) Listar Clientes");
        System.out.println("3) Cadastrar Item no Cardápio");
        System.out.println("4) Listar Itens do Cardápio");
        System.out.println("5) Criar Pedido");
        System.out.println("6) Avançar Status de Pedido");
        System.out.println("7) Listar Pedidos por Status");
        System.out.println("8) Relatório de Vendas (Simplificado)");
        System.out.println("9) Relatório de Vendas (Detalhado)");
        System.out.println("0) Sair");
        System.out.println("=====================================");
    }

    // Clientes
    private void cadastrarCliente() {
        System.out.println("--- Cadastrar Cliente ---");
        String nome = lerLinha("Nome: ");
        String fone = lerLinha("Telefone: ");
        try {
            Cliente c = new Cliente(nome, fone);
            db.adicionarCliente(c);
            System.out.println("Cliente cadastrado: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("--- Clientes ---");
        List<Cliente> lista = db.listarClientes();
        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : lista) System.out.println(c);
    }

    // Itens
    private void cadastrarItem() {
        System.out.println("--- Cadastrar Item ---");
        String nome = lerLinha("Nome do item: ");
        String precoStr = lerLinha("Preço (use ponto, ex: 12.50): ");
        try {
            BigDecimal preco = new BigDecimal(precoStr);
            ItemCardapio item = new ItemCardapio(nome, preco);
            db.adicionarItem(item);
            System.out.println("Item cadastrado: " + item);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarItens() {
        System.out.println("--- Itens do Cardápio ---");
        List<ItemCardapio> lista = db.listarItens();
        if (lista.isEmpty()) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }
        for (ItemCardapio i : lista) System.out.println(i);
    }

    // Pedidos
    private void criarPedido() {
        System.out.println("--- Criar Pedido ---");
        listarClientes();
        int idCliente = lerInt("Digite o ID do cliente: ");
        Cliente cliente = db.buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Pedido pedido = new Pedido(cliente);

        while (true) {
            listarItens();
            int codigo = lerInt("Código do item (0 para finalizar): ");
            if (codigo == 0) break;

            ItemCardapio item = db.buscarItemPorCodigo(codigo);
            if (item == null) {
                System.out.println("Item não encontrado.");
                continue;
            }

            int qtd = lerInt("Quantidade: ");
            try {
                PedidoItem pi = new PedidoItem(item, qtd);
                pedido.adicionarItem(pi);
                System.out.println("Adicionado: " + pi);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        if (pedido.getItens().isEmpty()) {
            System.out.println("Pedido vazio. Cancelado.");
            return;
        }

        db.adicionarPedido(pedido);
        System.out.println("Pedido criado: " + pedido);
    }

    private void avancarStatusPedido() {
        System.out.println("--- Avançar Status de Pedido ---");
        listarPedidosResumo();
        int numero = lerInt("Número do pedido: ");
        Pedido p = db.buscarPedidoPorNumero(numero);
        if (p == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
        try {
            p.avancarStatus();
            System.out.println("Novo status: " + p.getStatus());
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarPedidosPorStatus() {
        System.out.println("--- Pedidos por Status ---");
        mostrarStatusPossiveis();
        String s = lerLinha("Digite o status (maiúsculas/minúsculas não importam): ");
        try {
            PedidoStatus st = PedidoStatus.valueOf(s.trim().toUpperCase(Locale.ROOT));
            List<Pedido> lista = db.listarPedidosPorStatus(st);
            if (lista.isEmpty()) {
                System.out.println("Nenhum pedido com status " + st);
                return;
            }
            for (Pedido p : lista) System.out.println(p);
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido.");
        }
    }

    private void relatorioSimplificado() {
        System.out.println("--- Relatório de Vendas (Simplificado) ---");
        System.out.println("Total de pedidos: " + db.quantidadeTotalDePedidos());
        System.out.println("Valor total arrecadado: R$ " + db.valorTotalArrecadado().toPlainString());
    }

    private void relatorioDetalhado() {
        System.out.println("--- Relatório de Vendas (Detalhado) ---");
        List<Pedido> lista = db.listarPedidos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        for (Pedido p : lista) {
            System.out.println(p);
            for (PedidoItem item : p.getItens()) {
                System.out.println("  - " + item);
            }
        }
    }

    // util
    private int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = in.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private String lerLinha(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private void listarPedidosResumo() {
        List<Pedido> lista = db.listarPedidos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        for (Pedido p : lista) System.out.println(p);
    }

    private void mostrarStatusPossiveis() {
        for (PedidoStatus st : PedidoStatus.values()) {
            System.out.println(" - " + st);
        }
    }
}