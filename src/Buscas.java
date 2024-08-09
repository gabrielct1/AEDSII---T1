import java.io.*;
import java.nio.charset.StandardCharsets;

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

                    exibirDadosRegistro(arquivo, nome, id, especialidadeOuDataNascimento, crmOuTelefone);
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

    public static void buscaBinaria(String arquivo, int idProcurado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            long esquerda = 0;
            long direita = raf.length();

            while (esquerda < direita) {
                long meio = (esquerda + direita) / 2;

                raf.seek(meio);
                ajustarPosicaoRegistro(raf);

                String nome = raf.readUTF();
                int id = raf.readInt();
                String especialidadeOuDataNascimento = raf.readUTF();
                String crmOuTelefone = raf.readUTF();

                int tamanhoRegistro = calcularTamanhoRegistro(nome, id, especialidadeOuDataNascimento, crmOuTelefone);

                if (id == idProcurado) {
                    exibirDadosRegistro(arquivo, nome, id, especialidadeOuDataNascimento, crmOuTelefone);
                    return;
                } else if (id < idProcurado) {
                    esquerda = meio + tamanhoRegistro;
                } else {
                    direita = meio - tamanhoRegistro;
                }
            }

            System.out.println("Pessoa com ID " + idProcurado + " não encontrada.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void ajustarPosicaoRegistro(RandomAccessFile raf) throws IOException {
        // Reposiciona para o início do registro caso meio não esteja alinhado
        while (raf.getFilePointer() > 0) {
            raf.seek(raf.getFilePointer() - 1);
            if (raf.readByte() == 0) {
                break;
            }
            raf.seek(raf.getFilePointer() - 1);
        }
    }

    public static void exibirDadosRegistro(String arquivo, String nome, int id, String especialidadeOuDataNascimento, String crmOuTelefone) {
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
        }
    }

    public static int calcularTamanhoRegistro(String nome, int id, String especialidadeOuDataNascimento, String crmOuTelefone) {
        int tamanhoRegistro = 0;

        tamanhoRegistro += nome.getBytes(StandardCharsets.UTF_8).length + 2;
        tamanhoRegistro += Integer.BYTES;
        tamanhoRegistro += especialidadeOuDataNascimento.getBytes(StandardCharsets.UTF_8).length + 2;
        tamanhoRegistro += crmOuTelefone.getBytes(StandardCharsets.UTF_8).length + 2;

        return tamanhoRegistro;
    }
}
