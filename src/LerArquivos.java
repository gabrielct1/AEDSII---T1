import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("\n-------------- PACIENTES --------------\n");
            while (dis.available() > 0) {
                String nome = lerString(dis, TAMANHO_NOME).trim();
                int id = dis.readInt();
                String dataNascimento = lerString(dis, TAMANHO_DATA).trim();
                String telefone = lerString(dis, TAMANHO_TELEFONE).trim();
                System.out.printf("Nome: %s - ID: %d - Data de Nascimento: %-10s - Telefone: %-12s%n",
                        nome.trim(), id, dataNascimento, telefone);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de pacientes: " + e.getMessage());
        }
    }

    public static void lerMedicos(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            System.out.println("\n-------------- MEDICOS --------------\n");

            while (dis.available() > 0) {
                String nome = lerString(dis, TAMANHO_NOME).trim();
                int id = dis.readInt();
                String especialidade = lerString(dis, TAMANHO_ESPECIALIDADE).trim();
                String crm = lerString(dis, TAMANHO_CRM).trim();
                System.out.printf("Nome: %s - ID: %d - Especialidade: %s - CRM: %-7s%n",
                        nome.trim(), id, especialidade, crm);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de médicos: " + e.getMessage());
        }
    }

    public static void lerConsultas(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            System.out.println("\n-------------- CONSULTAS --------------\n");
            while (dis.available() > 0) {
                int idConsulta = dis.readInt();
                int pacienteId = dis.readInt();
                int medicoId = dis.readInt();
                String data = lerString(dis, TAMANHO_DATA).trim();
                String hora = lerString(dis, TAMANHO_HORA).trim();
                System.out.printf("Id da Consulta: %d - Id do Paciente: %d - Id do Médico: %d - Data: %-10s - Hora: %-5s%n",
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

    public static List<Integer> extrairIdsPacientes(String caminho) {
        List<Integer> ids = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                dis.skipBytes(TAMANHO_NOME); // Pular nome
                int id = dis.readInt();
                dis.skipBytes(TAMANHO_DATA + TAMANHO_TELEFONE); // Pular data e telefone
                ids.add(id);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de pacientes: " + e.getMessage());
        }
        return ids;
    }

    public static List<Integer> extrairIdsMedicos(String caminho) {
        List<Integer> ids = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                dis.skipBytes(TAMANHO_NOME); // Pular nome
                int id = dis.readInt();
                dis.skipBytes(TAMANHO_ESPECIALIDADE + TAMANHO_CRM); // Pular especialidade e CRM
                ids.add(id);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de médicos: " + e.getMessage());
        }
        return ids;
    }

    public static List<Integer> extrairIdsConsultas(String caminho) {
        List<Integer> ids = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            while (dis.available() > 0) {
                int idConsulta = dis.readInt();
                ids.add(idConsulta);
                dis.readInt(); // Pular pacienteId
                dis.readInt(); // Pular medicoId
                dis.skipBytes(TAMANHO_DATA + TAMANHO_HORA); // Pular data e hora
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de consultas: " + e.getMessage());
        }
        return ids;
    }

    public static void lerIdsPacientes(String caminho) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminho))) {
            System.out.println("\n-------------- PACIENTES ORDENADOS PELO ID --------------\n");
            while (dis.available() > 0) {
                int id= dis.readInt();
                System.out.printf("Paciente: %d\n", id);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de pacientes: " + e.getMessage());
        }
    }
}