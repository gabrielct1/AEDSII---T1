import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class LerArquivos {
    public static void exibirDadosPacientes(String caminho) {
        try (java.io.DataInputStream dis = new java.io.DataInputStream(new java.io.FileInputStream(caminho))) {
            System.out.println("\nDados dos Pacientes:");
            while (dis.available() > 0) {
                String nome = dis.readUTF();
                int id = dis.readInt();
                String dataNascimento = dis.readUTF();
                String telefone = dis.readUTF();
                System.out.printf("Nome: %s - ID: %d - Data de Nascimento: %s - Telefone: %s%n", nome, id, dataNascimento, telefone);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de pacientes: " + e.getMessage());
        }
    }

    public static void exibirDadosMedicos(String caminho) {
        try (java.io.DataInputStream dis = new java.io.DataInputStream(new java.io.FileInputStream(caminho))) {
            System.out.println("\nDados dos Médicos:");
            while (dis.available() > 0) {
                String nome = dis.readUTF();
                int id = dis.readInt();
                String especialidade = dis.readUTF();
                String crm = dis.readUTF();
                System.out.printf("Nome: %s - ID: %d - Especialidade: %s - CRM: %s%n", nome, id, especialidade, crm);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de médicos: " + e.getMessage());
        }
    }
}