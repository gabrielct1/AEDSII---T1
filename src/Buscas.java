import java.io.*;

public class Buscas {

    public static void buscaSequencial(String arquivo, int idProcurado) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(arquivo))) {
            boolean encontrado = false;

            while (dis.available() > 0) {
                String nome = dis.readUTF();
                int id = dis.readInt();
                String especialidadeOuDataNascimento = dis.readUTF();
                String crmOuTelefone = dis.readUTF();

                if (id == idProcurado) {
                    encontrado = true;

                    if (arquivo.contains("pacientes")) {
                        System.out.println("Nome: " + nome);
                        System.out.println("ID: " + id);
                        System.out.println("Data de Nascimento: " + especialidadeOuDataNascimento);
                        System.out.println("Telefone: " + crmOuTelefone);

                    } else if (arquivo.contains("medicos")) {
                        System.out.println("Nome: " + nome);
                        System.out.println("ID: " + id);
                        System.out.println("Especialidade: " + especialidadeOuDataNascimento);
                        System.out.println("CRM: " + crmOuTelefone);
                    } else {
                        System.out.println("Arquivo não reconhecido.");
                    }
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Pessoa com ID " + idProcurado + " não encontrada.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void buscaBinaria(String arquivo, int idProcurado, Pessoa pessoa) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            long esquerda = 0;
            long direita = (raf.length() / getRegistroTamanho()) - 1;

            while (esquerda <= direita) {
                long meio = (esquerda + direita) / 2;
                raf.seek(meio * getRegistroTamanho());

                String nome = raf.readUTF();
                int id = raf.readInt();
                String especialidadeOuDataNascimento = raf.readUTF();
                String crmOuTelefone = raf.readUTF();

                if (id == idProcurado) {
                    // Encontrado
                    if (pessoa instanceof Paciente) {
                        Paciente paciente = new Paciente(
                                nome,
                                id,
                                especialidadeOuDataNascimento, // Data de Nascimento
                                crmOuTelefone  // Telefone
                        );
                        paciente.imprimeInfo();
                    } else if (pessoa instanceof Medico) {
                        Medico medico = new Medico(
                                nome,
                                id,
                                especialidadeOuDataNascimento, // Especialidade
                                crmOuTelefone  // CRM
                        );
                        medico.imprimeInfo();
                    }
                    return;
                } else if (id < idProcurado) {
                    esquerda = meio + 1;
                } else {
                    direita = meio - 1;
                }
            }

            System.out.println("Pessoa com ID " + idProcurado + " não encontrada.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static int getRegistroTamanho() {

        return 128;
    }
}