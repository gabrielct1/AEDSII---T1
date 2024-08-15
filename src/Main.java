import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        int opc = 0, arquivoPesquisa, idProcurado;
        String nomeArquivo = "";

        Scanner in = new Scanner(System.in);

        CriarBases.criarBasesDesordenadas();

        while(opc != 9){
            System.out.println("\nQual operação deseja realizar?");
            System.out.println("[1] Inserir em uma base.");
            System.out.println("[2] Excluir de uma base.");
            System.out.println("[3] Realizar uma busca sequencial.");
            System.out.println("[4] Realizar uma busca binária.");
            System.out.println("[5] Exibir base de pacientes.");
            System.out.println("[6] Exibir base de médicos.");
            System.out.println("[7] Exibir base de consultas.");
            System.out.println("[8] Ordenar uma base.");
            System.out.println("[9] Sair.");

            System.out.printf("\nOpção: ");
            opc = in.nextInt();

            switch(opc){
                case 1:
                    System.out.println("\nEscolha uma base de dados para inserção: ");
                    System.out.println("[1] Pacientes.");
                    System.out.println("[2] Médicos.");
                    System.out.println("[3] Consultas.");

                    System.out.printf("\nOpção: ");
                    arquivoPesquisa = in.nextInt();

                    if (arquivoPesquisa == 1) {
                        Operacoes.inserirPacientes();
                    } else if (arquivoPesquisa == 2) {
                        Operacoes.inserirMedicos();
                    } else if(arquivoPesquisa == 3) {
                        Operacoes.inserirConsultas();
                    } else{
                        System.out.println("Opção inválida.");
                    }

                    break;
                case 2:
                    System.out.println("\nEscolha uma base de dados para exclusão: ");
                    System.out.println("[1] Pacientes.");
                    System.out.println("[2] Médicos.");
                    System.out.println("[3] Consultas.");

                    System.out.printf("Opção: ");
                    arquivoPesquisa = in.nextInt();

                    System.out.printf("\nInforme o ID que deseja excluir: ");
                    int idParaExcluir = in.nextInt();

                    if (arquivoPesquisa == 1) {
                        String teste = "pacientes.dat";
                        Operacoes.excluirPacientes(teste, idParaExcluir);
                    } else if (arquivoPesquisa == 2) {
                        String teste = "medicos.dat";
                        Operacoes.excluirMedicos(teste, idParaExcluir);
                    } else if (arquivoPesquisa == 3) {
                        String teste = "consultas.dat";
                        Operacoes.excluirConsultas(teste, idParaExcluir);
                    } else{
                        System.out.println("Opção inválida.");
                    }
                    break;
                case 3:
                    System.out.println("\nEm qual base de dados você deseja pesquisar?\n[1] Pacientes.\n[2] Médicos.\n[3] Consultas.");
                    System.out.printf("Opção: ");
                    arquivoPesquisa = in.nextInt();

                    System.out.printf("\nInforme o ID a ser procurado: ");
                    idProcurado = in.nextInt();
                    in.nextLine();

                    System.out.println();

                    if (arquivoPesquisa == 1) {
                        nomeArquivo = "pacientes.dat";
                        Buscas.buscaSequencialPacientes(nomeArquivo, idProcurado);
                    } else if (arquivoPesquisa == 2) {
                        nomeArquivo = "medicos.dat";
                        Buscas.buscaSequencialMedicos(nomeArquivo, idProcurado);
                    } else if (arquivoPesquisa == 3){
                        nomeArquivo = "consultas.dat";
                        Buscas.buscaSequencialConsulta(nomeArquivo, idProcurado);
                    } else{
                        System.out.println("Opção inválida.");
                    }
                    break;
                case 4:
                    System.out.println("\nQual base de dados você deseja pesquisar?\n[1] Pacientes.\n[2] Médicos.\n[3] Consultas.");
                    System.out.printf("Opção: ");
                    arquivoPesquisa = in.nextInt();

                    System.out.printf("\nInforme o ID a ser procurado: ");
                    idProcurado = in.nextInt();
                    in.nextLine();

                    System.out.println();

                    if (arquivoPesquisa == 1) {
                        nomeArquivo = "pacientes_ordenados.dat";
                        Buscas.buscaBinariaPacientes(nomeArquivo, idProcurado);
                    } else if(arquivoPesquisa == 2){
                        nomeArquivo = "medicos_ordenados.dat";
                        Buscas.buscaBinariaMedicos(nomeArquivo, idProcurado);
                    } else if(arquivoPesquisa == 3 ){
                        nomeArquivo = "consultas_ordenadas.dat";
                        Buscas.buscaBinariaConsulta(nomeArquivo, idProcurado);
                    } else{
                        System.out.println("Opção inválida.");
                    }

                    break;
                case 5:
                    LerArquivos.lerPacientes("pacientes.dat");
                    break;
                case 6:
                    LerArquivos.lerMedicos("medicos.dat");
                    break;
                case 7:
                    LerArquivos.lerConsultas("consultas.dat");
                    break;
                case 8:
                    System.out.println("\nEscolha uma base de dados para ordenar: ");
                    System.out.println("[1] Pacientes.");
                    System.out.println("[2] Médicos.");
                    System.out.println("[3] Consultas.");

                    System.out.printf("Opção: ");
                    arquivoPesquisa = in.nextInt();

                    if (arquivoPesquisa == 1) {
                        String arquivoEntrada = "pacientes.dat";
                        String arquivoSaida = "pacientes_ordenados.dat";

                        QuickSortPacientes.ordenarDisco(arquivoEntrada, arquivoSaida);
                        LerArquivos.lerPacientes("pacientes_ordenados.dat");
                    } else if (arquivoPesquisa == 2) {
                        String arquivoEntrada = "medicos.dat";
                        String arquivoSaida = "medicos_ordenados.dat";

                        QuickSortMedicos.ordenarDisco(arquivoEntrada, arquivoSaida);
                        LerArquivos.lerMedicos("medicos_ordenados.dat");
                    } else if (arquivoPesquisa == 3) {
                        String arquivoEntrada = "consultas.dat";
                        String arquivoSaida = "consultas_ordenadas.dat";

                        QuickSortConsultas.ordenarDisco(arquivoEntrada, arquivoSaida);
                        LerArquivos.lerConsultas("consultas_ordenadas.dat");
                    }
                    break;
                case 9:
                    System.out.println("Saindo...");
                    opc = 9;
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }
        }
    }
}