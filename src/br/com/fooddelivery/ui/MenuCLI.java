package br.com.fooddelivery.ui;

import java.util.Locale;
import java.util.Scanner;

public class MenuCLI {

    private final Scanner in = new Scanner(System.in).useLocale(Locale.US);

    public void iniciar() {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1,2,3,4,5,6,7,8,9 -> System.out.println("Funcionalidade em desenvolvimento.");
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

