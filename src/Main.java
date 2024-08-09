import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int opc = 0;
        String nomeArquivo = "";
        int arquivoPesquisa;
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
                    //Inserir uma base
                    break;
                case 2:
                    //Excluir uma base
                    break;
                case 3:
                    System.out.println("Informe o ID a ser procurado: ");
                    int idProcurado = in.nextInt();
                    in.nextLine();
                    System.out.println("Qual base de dados você deseja pesquisar?\n[0] Médicos\n[1] Pacientes");
                    arquivoPesquisa = in.nextInt();
                    if (arquivoPesquisa == 0) {
                        nomeArquivo = "medicos.dat";
                    } else if (arquivoPesquisa == 1) {
                        nomeArquivo = "pacientes.dat";
                    }
                    Buscas.buscaSequencial(nomeArquivo, idProcurado);
                    break;
                case 4:
                    //busca binaria();
                case 5:
                    LerArquivos.exibirDadosPacientes("C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\pacientes.dat");
                    break;
                case 6:
                    LerArquivos.exibirDadosMedicos("C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\medicos.dat");
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