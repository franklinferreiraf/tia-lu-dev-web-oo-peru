package br.com.fooddelivery.ui;

import br.com.fooddelivery.domain.Cliente;
import br.com.fooddelivery.domain.ItemCardapio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuCLI {

    private final Scanner in = new Scanner(System.in).useLocale(Locale.US);

    private final List<Cliente> clientes = new ArrayList<>();
    private final List<ItemCardapio> itens = new ArrayList<>();

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
                case 5,6,7,8,9 -> System.out.println("Funcionalidade em desenvolvimento.");
                case 0 -> System.out.println("Saindo... Valeu!");
                default -> System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (opcao != 0);
    }

    // Clientes
    private void cadastrarCliente() {
        System.out.println("--- Cadastrar Cliente ---");
        System.out.print("Nome: ");
        String nome = in.nextLine().trim();
        System.out.print("Telefone: ");
        String fone = in.nextLine().trim();
        try {
            Cliente c = new Cliente(nome, fone);
            clientes.add(c);
            System.out.println("Cliente cadastrado: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private void listarClientes() {
        System.out.println("--- Clientes ---");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) System.out.println(c);
    }

    // Itens
    private void cadastrarItem() {
        System.out.println("--- Cadastrar Item ---");
        System.out.print("Nome do item: ");
        String nome = in.nextLine().trim();
        System.out.print("Preço (use ponto, ex: 12.50): ");
        String precoStr = in.nextLine().trim();
        try {
            BigDecimal preco = new BigDecimal(precoStr);
            ItemCardapio item = new ItemCardapio(nome, preco);
            itens.add(item);
            System.out.println("Item cadastrado: " + item);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private void listarItens() {
        System.out.println("--- Itens do Cardápio ---");
        if (itens.isEmpty()) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }
        for (ItemCardapio i : itens) System.out.println(i);
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
}
