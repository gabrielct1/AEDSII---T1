import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int opc = 0;

        Scanner in = new Scanner(System.in);

        CriarBases.criarBasesDesordenadas();

        while(opc != 8){
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Inserir em uma base");
            System.out.println("2. Excluir de uma base");
            System.out.println("3. Realizar uma busca sequencial");
            System.out.println("4. Realizar uma busca binária");
            System.out.println("5. Exibir base de pacientes");
            System.out.println("6. Exibir base de médicos");
            System.out.println("7. Exibir base de consultas");
            System.out.println("8. Sair");

            System.out.println("\nOpção: ");
            opc = in.nextInt();

            switch(opc){
                case 1:
                    Operacoes.inserir();
                    break;
                case 2:
                    Operacoes.excluir();
                    break;
                case 3:
                    //busca sequencial();
                    break;
                case 4:
                    //busca binaria();
                case 5:
                    LerArquivos.exibirDadosPacientes("C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\pacientes.dat");
                    break;
                case 6:
                    LerArquivos.exibirDadosMedicos("C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\medicos.dat");
                    break;
                case 7:
                    //exibir base de consultas
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!\n");
            }
        }
    }
}