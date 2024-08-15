import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Operacoes {

    private static final int TAMANHO_NOME = 50;
    private static final int TAMANHO_ID = 4; // int
    private static final int TAMANHO_DATA = 10;
    private static final int TAMANHO_TELEFONE = 12;

    private static final int TAMANHO_ESPECIALIDADE = 25;
    private static final int TAMANHO_CRM = 7;

    private static final int TAMANHO_HORA = 5; // "HH:MM" é sempre 5 bytes

    public static void inserirPacientes() throws IOException {
        Scanner in = new Scanner(System.in);
        String nomeArquivo = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\pacientes.dat";

        System.out.println("\nInforme o nome do paciente:");
        String nome = in.nextLine();
        nome = String.format("%-50s", nome);

        System.out.println("Informe o ID:");
        int id = in.nextInt();
        in.nextLine(); // Limpar o buffer

        System.out.println("Informe a data de nascimento:");
        String data = in.nextLine();
        data = String.format("%-10s", data);

        System.out.println("Informe um telefone para contato:");
        String telefone = in.nextLine();
        telefone = String.format("%-12s", telefone);

        Paciente novoPaciente = new Paciente(nome, id, data, telefone);
        int tamanhoRegistro = novoPaciente.calcularTamanhoRegistro();

        try (RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw")) {
            long posicaoAtual = arquivo.length();
            arquivo.seek(posicaoAtual);

            arquivo.writeBytes(novoPaciente.getNome());
            arquivo.writeInt(novoPaciente.getId());
            arquivo.writeBytes(novoPaciente.getDataNascimento());
            arquivo.writeBytes(novoPaciente.getTelefone());
        }

        System.out.println("\nDados adicionados com sucesso!");
    }

    public static void inserirMedicos() throws IOException {
        Scanner in = new Scanner(System.in);
        String nomeArquivo = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\medicos.dat";

        System.out.println("\nInforme o nome do médico:");
        String nome = in.nextLine();
        nome = String.format("%-50s", nome);

        System.out.println("Informe o ID:");
        int id = in.nextInt();
        in.nextLine(); // Limpar o buffer

        System.out.println("Informe a especialidade:");
        String especialidade = in.nextLine();
        especialidade = String.format("%-25s", especialidade);

        System.out.println("Informe o CRM:");
        String crm = in.nextLine();
        crm = String.format("%-7s", crm);

        Medico novoMedico = new Medico(nome, id, especialidade, crm);

        try (RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw")) {
            long posicaoAtual = arquivo.length();
            arquivo.seek(posicaoAtual);

            arquivo.writeBytes(novoMedico.getNome());
            arquivo.writeInt(novoMedico.getId());
            arquivo.writeBytes(novoMedico.getEspecialidade());
            arquivo.writeBytes(novoMedico.getCrm());
        }

        System.out.println("\nDados adicionados com sucesso!");
    }

    public static void inserirConsultas() throws IOException {
        Scanner in = new Scanner(System.in);
        String nomeArquivo = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\consultas.dat";

        System.out.println("\nInforme o ID da consulta que deseja inserir:");
        int idConsulta = in.nextInt();

        System.out.println("Informe o ID do paciente:");
        int idPaciente = in.nextInt();

        System.out.println("Informe o ID do médico:");
        int idMedico = in.nextInt();
        in.nextLine(); // Limpar o buffer

        System.out.println("Data da consulta:");
        String dataConsulta = in.nextLine();

        System.out.println("Hora:");
        String hora = in.nextLine();

        Consulta novaConsulta = new Consulta(idConsulta, idPaciente, idMedico, dataConsulta, hora);

        try (RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw")) {
            long posicaoAtual = arquivo.length();
            arquivo.seek(posicaoAtual);

            arquivo.writeInt(novaConsulta.getIdConsulta());
            arquivo.writeInt(novaConsulta.getPacienteId());
            arquivo.writeInt(novaConsulta.getMedicoId());
            arquivo.writeBytes(novaConsulta.getData());
            arquivo.writeBytes(novaConsulta.getHora());
        }

        System.out.println("\nDados adicionados com sucesso!");
    }

    public static void excluirPacientes(String nomeArquivo, int id) throws IOException {
        File arquivoOriginal = new File(nomeArquivo);
        File arquivoTemp = new File(nomeArquivo + ".tmp.dat");

        try (RandomAccessFile arquivo = new RandomAccessFile(arquivoOriginal, "r");
             RandomAccessFile arquivoTempRa = new RandomAccessFile(arquivoTemp, "rw")) {

            while (arquivo.getFilePointer() < arquivo.length()) {
                String nome = lerString(arquivo, 50);
                int idRegistro = arquivo.readInt();
                String data = lerString(arquivo, 10);
                String telefone = lerString(arquivo, 12);

                if (idRegistro != id) {
                    escreverCampo(arquivoTempRa, nome, 50);
                    arquivoTempRa.writeInt(idRegistro);
                    escreverCampo(arquivoTempRa, data, 10);
                    escreverCampo(arquivoTempRa, telefone, 12);
                }
            }
        }

        if (!arquivoOriginal.delete()) {
            System.out.println("\nNão foi possível excluir o arquivo original.");
            return;
        }

        if (!arquivoTemp.renameTo(arquivoOriginal)) {
            System.out.println("\nNão foi possível renomear o arquivo temporário.");
        } else {
            System.out.println("\nRegistro excluído com sucesso!");
        }
    }

    public static void excluirMedicos(String nomeArquivo, int id) throws IOException {
        File arquivoOriginal = new File(nomeArquivo);
        File arquivoTemp = new File(nomeArquivo + ".tmp.dat");

        try (RandomAccessFile arquivo = new RandomAccessFile(arquivoOriginal, "r");
             RandomAccessFile arquivoTempRa = new RandomAccessFile(arquivoTemp, "rw")) {

            while (arquivo.getFilePointer() < arquivo.length()) {
                String nome = lerString(arquivo, 50);
                int idRegistro = arquivo.readInt();
                String data = lerString(arquivo, 25);
                String telefone = lerString(arquivo, 7);

                if (idRegistro != id) {
                    escreverCampo(arquivoTempRa, nome, 50);
                    arquivoTempRa.writeInt(idRegistro);
                    escreverCampo(arquivoTempRa, data, 25);
                    escreverCampo(arquivoTempRa, telefone, 7);
                }
            }
        }

        if (!arquivoOriginal.delete()) {
            System.out.println("\nNão foi possível excluir o arquivo original.");
            return;
        }

        if (!arquivoTemp.renameTo(arquivoOriginal)) {
            System.out.println("\nNão foi possível renomear o arquivo temporário.");
        } else {
            System.out.println("\nRegistro excluído com sucesso!");
        }
    }

    public static void excluirConsultas(String nomeArquivo, int id) throws IOException {
        File arquivoOriginal = new File(nomeArquivo);
        File arquivoTemp = new File(nomeArquivo + ".tmp.dat");

        try (RandomAccessFile arquivo = new RandomAccessFile(arquivoOriginal, "r");
             RandomAccessFile arquivoTempRa = new RandomAccessFile(arquivoTemp, "rw")) {

            while (arquivo.getFilePointer() < arquivo.length()) {
                int idConsulta = arquivo.readInt();
                int pacienteId = arquivo.readInt();
                int medicoId = arquivo.readInt();
                String data = lerString(arquivo, TAMANHO_DATA);
                String hora = lerString(arquivo, TAMANHO_HORA);

                if (idConsulta != id) {
                    arquivoTempRa.writeInt(idConsulta);
                    arquivoTempRa.writeInt(pacienteId);
                    arquivoTempRa.writeInt(medicoId);
                    escreverCampo(arquivoTempRa, data, 10);
                    escreverCampo(arquivoTempRa, hora, 5);
                }
            }
        }

        if (!arquivoOriginal.delete()) {
            System.out.println("\nNão foi possível excluir o arquivo original.");
            return;
        }

        if (!arquivoTemp.renameTo(arquivoOriginal)) {
            System.out.println("\nNão foi possível renomear o arquivo temporário.");
        } else {
            System.out.println("\nRegistro excluído com sucesso!");
        }
    }

    private static String lerString(RandomAccessFile raf, int tamanho) throws IOException {
        byte[] bytes = new byte[tamanho];
        raf.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static void escreverCampo(RandomAccessFile dos, String campo, int tamanho) throws IOException {
        byte[] bytes = campo.getBytes(StandardCharsets.UTF_8);
        dos.write(bytes, 0, Math.min(tamanho, bytes.length));
        // Preencher o restante com espaços para garantir o tamanho fixo
        for (int i = bytes.length; i < tamanho; i++) {
            dos.writeByte(' ');
        }
    }
}
