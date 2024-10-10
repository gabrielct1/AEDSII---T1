import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TabelaHashDisco tabelaHash = new TabelaHashDisco(100); // Define o tamanho da tabela hash

        while (true) {
            System.out.println("\nQual operação deseja realizar?");
            System.out.println("[15] Inserir paciente utilizando Tabela Hash.");
            System.out.println("[16] Buscar paciente utilizando Tabela Hash.");
            System.out.println("[17] Excluir paciente utilizando Tabela Hash.");
            System.out.println("[18] Exibir Tabela Hash.");
            System.out.println("[19] Sair.");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 15: // Inserir paciente
                    System.out.print("Informe o ID do paciente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    System.out.print("Informe o nome do paciente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Informe o telefone do paciente: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Informe a data de nascimento do paciente (DDMMAAAA): ");
                    String dataNascimento = scanner.nextLine();

                    PacienteS paciente = new PacienteS(id, nome, telefone, dataNascimento);
                    tabelaHash.inserir(paciente);
                    break;

                case 16:
                    System.out.print("Informe o ID do paciente para buscar: ");
                    int idBusca = scanner.nextInt();
                    tabelaHash.buscar(idBusca);
                    break;

                case 17:
                    System.out.print("Informe o ID do paciente para excluir: ");
                    int idExclusao = scanner.nextInt();
                    tabelaHash.excluir(idExclusao);
                    break;

                case 18: // Exibir tabela hash
                    tabelaHash.exibirTabelaHash();
                    break;

                case 19: // Sair
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
