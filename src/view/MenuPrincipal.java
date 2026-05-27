package src.view;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;

    public MenuPrincipal() {
        // Corrigido aqui: mudamos para System.in purinho
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println("        SISTEMA EVOLUTEA         ");
            System.out.println("=================================");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Tutores");
            System.out.println("3. Gerenciar Especialistas");
            System.out.println("4. Registrar Evolução/Consulta");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("\n[Erro] Por favor, digite apenas números inteiros.");
            }
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("\n-> Entrando no menu de Pacientes... (Em desenvolvimento)");
                break;
            case 2:
                System.out.println("\n-> Entrando no menu de Tutores... (Em desenvolvimento)");
                break;
            case 3:
                System.out.println("\n-> Entrando no menu de Especialistas... (Em desenvolvimento)");
                break;
            case 4:
                System.out.println("\n-> Iniciando registro de evolução... (Em desenvolvimento)");
                break;
            case 0:
                System.out.println("\nEncerrando o EvoluTEA. Até logo!");
                break;
            default:
                System.out.println("\n[Aviso] Opção inválida! Escolha um número do menu.");
                break;
        }
    }
}