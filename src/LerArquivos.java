import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class LerArquivos {

    private static final int TAMANHO_NOME = 50;
    private static final int TAMANHO_ID = 4; // int
    private static final int TAMANHO_DATA = 10;
    private static final int TAMANHO_TELEFONE = 12;

    private static final int TAMANHO_ESPECIALIDADE = 25;
    private static final int TAMANHO_CRM = 7;

    private static final int TAMANHO_HORA = 5;

    public static void lerPacientes(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                String nome = lerString(dis, TAMANHO_NOME).trim();
                int id = dis.readInt();
                String dataNascimento = lerString(dis, TAMANHO_DATA).trim();
                String telefone = lerString(dis, TAMANHO_TELEFONE).trim();
                System.out.printf("Paciente: Nome: %s - ID: %d - Data de Nascimento: %-10s - Telefone: %-12s%n",
                        nome.trim(), id, dataNascimento, telefone);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de pacientes: " + e.getMessage());
        }
    }

    public static void lerMedicos(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                String nome = lerString(dis, TAMANHO_NOME).trim();
                int id = dis.readInt();
                String especialidade = lerString(dis, TAMANHO_ESPECIALIDADE).trim();
                String crm = lerString(dis, TAMANHO_CRM).trim();
                System.out.printf("Médico: Nome: %s - ID: %d - Especialidade: %s - CRM: %-7s%n",
                        nome.trim(), id, especialidade, crm);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de médicos: " + e.getMessage());
        }
    }

    public static void lerConsultas(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                int idConsulta = dis.readInt();
                int pacienteId = dis.readInt();
                int medicoId = dis.readInt();
                String data = lerString(dis, TAMANHO_DATA).trim();
                String hora = lerString(dis, TAMANHO_HORA).trim();
                System.out.printf("Consulta: ID: %d - Paciente ID: %d - Médico ID: %d - Data: %-10s - Hora: %-5s%n",
                        idConsulta, pacienteId, medicoId, data.trim(), hora.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de consultas: " + e.getMessage());
        }
    }

    private static String lerString(DataInputStream dis, int tamanho) throws IOException {
        byte[] bytes = new byte[tamanho];
        dis.readFully(bytes);
        return new String(bytes, "UTF-8");
    }
}