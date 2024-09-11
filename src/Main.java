import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        int opc = 0, arquivoPesquisa, idProcurado;

        int numParticoesMedicos = 0;
        int numParticoesPacientes = 0;
        int numParticoesConsultas = 0;

        String nomeArquivo = "";

        Scanner in = new Scanner(System.in);

        CriarBases.criarBasesDesordenadas();

        while(opc != 12){
            System.out.println("\nQual operação deseja realizar?");
            System.out.println("[01] Inserir em uma base.");
            System.out.println("[02] Excluir de uma base.");
            System.out.println("[03] Realizar uma busca sequencial.");
            System.out.println("[04] Realizar uma busca binária.");
            System.out.println("[05] Exibir base de pacientes.");
            System.out.println("[06] Exibir base de médicos.");
            System.out.println("[07] Exibir base de consultas.");
            System.out.println("[08] Ordernar uma base.");
            System.out.println("[09] Gerar partições de pacientes.");
            System.out.println("[10] Gerar partições de médicos.");
            System.out.println("[11] Gerar partições de consultas.");
            System.out.println("[12] Intercalar partições de pacientes");
            System.out.println("[13] Intercalar partições de médicos");
            System.out.println("[14] Intercalar partições de consultas");
            System.out.println("[15] Sair.");

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
                    List<Integer> idsPacientes = LerArquivos.extrairIdsPacientes("pacientes.dat");
                    numParticoesPacientes = SelecaoSubstituicao.gerarParticoes("pacientes", idsPacientes);
                    System.out.println("Número de partições geradas para pacientes: " + numParticoesPacientes);
                    break;
                case 10:
                    List<Integer> idsMedicos = LerArquivos.extrairIdsMedicos("medicos.dat");
                    numParticoesMedicos = SelecaoSubstituicao.gerarParticoes("medicos", idsMedicos);
                    System.out.println("Número de partições geradas para médicos: " + numParticoesMedicos);
                    break;
                case 11:
                    List<Integer> idsConsultas = LerArquivos.extrairIdsConsultas("consultas.dat");
                    numParticoesConsultas = SelecaoSubstituicao.gerarParticoes("consultas", idsConsultas);
                    System.out.println("Número de partições geradas para consultas: " + numParticoesConsultas);
                    break;
                case 12:
                    ArvoreVencedoresIntercalacao.intercalarParticoes(numParticoesPacientes, "pacientes", "pacientes_intercalados");
                    break;
                case 13:
                    ArvoreVencedoresIntercalacao.intercalarParticoes(numParticoesMedicos, "medicos", "medicos_intercalados");
                    break;
                case 14:
                    ArvoreVencedoresIntercalacao.intercalarParticoes(numParticoesConsultas, "consultas", "consultas_intercaladas");
                    break;
                case 15:
                    System.out.println("Saindo...");
                    opc = 15;
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }
        }
    }
}