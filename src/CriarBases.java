import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CriarBases {

    private static final int TAMANHO_NOME = 50;
    private static final int TAMANHO_ID = 4; // int
    private static final int TAMANHO_DATA = 10;
    private static final int TAMANHO_TELEFONE = 12;
    private static final int TAMANHO_REGISTRO_PACIENTE = TAMANHO_NOME + TAMANHO_ID + TAMANHO_DATA + TAMANHO_TELEFONE;

    private static final int TAMANHO_ESPECIALIDADE = 25;
    private static final int TAMANHO_CRM = 7;
    private static final int TAMANHO_REGISTRO_MEDICO = TAMANHO_NOME + TAMANHO_ID + TAMANHO_ESPECIALIDADE + TAMANHO_CRM;

    private static final int TAMANHO_HORA = 5; // "HH:MM" é sempre 5 bytes
    private static final int TAMANHO_REGISTRO_CONSULTA = 4 + 4 + 4 + TAMANHO_DATA + TAMANHO_HORA; // 4 bytes cada int + data + hora

    public static void criarBasesDesordenadas() {
        List<Paciente> pacientes = new ArrayList<>();
        List<Medico> medicos = new ArrayList<>();
        List<Consulta> consultas = new ArrayList<>();
        Random random = new Random();

        int dia = 1;
        int mes = 8;
        int ano = 2024;

        for (int i = 1000; i < 1200; i++) {
            Paciente p = new Paciente(padronizarNome("Ana"), i, "27/10/2003", padronizarTelefone("98627-9186"));
            pacientes.add(p);
        }

        for (int j = 2000; j < 2200; j++) {
            Medico m = new Medico(padronizarNome("Gabriel"), j, padronizarEspecialidade("Cliníco Geral"), "2347-MG");
            medicos.add(m);
        }

        for (int i = 1; i <= 200; i++) {
            int idPaciente = 1000 + random.nextInt(11);
            int idMedico = 2000 + random.nextInt(11);
            String data = String.format("%02d/%02d/%04d", dia, mes, ano);
            String hora = String.format("%02d:%02d", random.nextInt(24), random.nextInt(60));

            consultas.add(new Consulta(i, idPaciente, idMedico, data, hora));
            dia++;

            if (dia > 31) {
                dia = 1;
                mes++;
                if (mes > 12) {
                    mes = 1;
                    ano++;
                }
            }
        }

        Collections.shuffle(pacientes);
        Collections.shuffle(medicos);
        Collections.shuffle(consultas);

        String caminhoPacientes = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\pacientes.dat";
        String caminhoMedicos = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\medicos.dat";
        String caminhoConsultas = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\consultas.dat";

        try (DataOutputStream dosPacientes = new DataOutputStream(new FileOutputStream(caminhoPacientes))) {
            for (Paciente p : pacientes) {
                escreverRegistroPaciente(dosPacientes, p);
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo de pacientes: " + e.getMessage());
        }

        try (DataOutputStream dosMedicos = new DataOutputStream(new FileOutputStream(caminhoMedicos))) {
            for (Medico m : medicos) {
                escreverRegistroMedico(dosMedicos, m);
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo de médicos: " + e.getMessage());
        }

        try (DataOutputStream dosConsultas = new DataOutputStream(new FileOutputStream(caminhoConsultas))) {
            for (Consulta c : consultas) {
                escreverRegistroConsulta(dosConsultas, c);
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo de consultas: " + e.getMessage());
        }
    }

    private static void escreverRegistroPaciente(DataOutputStream dos, Paciente p) throws IOException {
        dos.write(p.getNome().getBytes("UTF-8"), 0, Math.min(TAMANHO_NOME, p.getNome().length()));
        dos.writeInt(p.getId());
        dos.write(p.getDataNascimento().getBytes("UTF-8"), 0, Math.min(TAMANHO_DATA, p.getDataNascimento().length()));
        dos.write(p.getTelefone().getBytes("UTF-8"), 0, Math.min(TAMANHO_TELEFONE, p.getTelefone().length()));

        // Preencher o restante com espaços para garantir que o tamanho total do registro seja 76 bytes
        int bytesEscritos = TAMANHO_NOME + TAMANHO_ID + TAMANHO_DATA + TAMANHO_TELEFONE;
        for (int i = bytesEscritos; i < TAMANHO_REGISTRO_PACIENTE; i++) {
            dos.writeByte(' '); // Preencher com espaços
        }
    }

    private static void escreverRegistroMedico(DataOutputStream dos, Medico m) throws IOException {
        dos.write(m.getNome().getBytes("UTF-8"), 0, Math.min(TAMANHO_NOME, m.getNome().length()));
        dos.writeInt(m.getId());
        dos.write(m.getEspecialidade().getBytes("UTF-8"), 0, Math.min(TAMANHO_ESPECIALIDADE, m.getEspecialidade().length()));
        dos.write(m.getCrm().getBytes("UTF-8"), 0, Math.min(TAMANHO_CRM, m.getCrm().length()));

        // Preencher o restante com espaços para garantir que o tamanho total do registro seja 86 bytes
        int bytesEscritos = TAMANHO_NOME + TAMANHO_ID + TAMANHO_ESPECIALIDADE + TAMANHO_CRM;
        for (int i = bytesEscritos; i < TAMANHO_REGISTRO_MEDICO; i++) {
            dos.writeByte(' '); // Preencher com espaços
        }
    }

    private static void escreverRegistroConsulta(DataOutputStream dos, Consulta c) throws IOException {
        dos.writeInt(c.getIdConsulta());
        dos.writeInt(c.getPacienteId());
        dos.writeInt(c.getMedicoId());
        dos.write(c.getData().getBytes("UTF-8"), 0, Math.min(TAMANHO_DATA, c.getData().length()));
        dos.write(c.getHora().getBytes("UTF-8"), 0, Math.min(TAMANHO_HORA, c.getHora().length()));
    }

    private static String padronizarNome(String nome) {
        return String.format("%-" + TAMANHO_NOME + "s", nome);
    }

    private static String padronizarTelefone(String telefone) {
        return String.format("%-" + TAMANHO_TELEFONE + "s", telefone);
    }

    private static String padronizarEspecialidade(String especialidade) {
        return String.format("%-" + TAMANHO_ESPECIALIDADE + "s", especialidade);
    }
}
