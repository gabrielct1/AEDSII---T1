import java.io.*;

public class Buscas {

    public void buscaSequencial(File arquivo, int idProcurado, Pessoa pessoa) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(arquivo))) {
            boolean encontrado = false;

            while (dis.available() > 0) {
                String nome = dis.readUTF();
                int id = dis.readInt();
                String especialidadeOuDataNascimento = dis.readUTF();
                String crmOuTelefone = dis.readUTF();

                if (id == idProcurado) {
                    encontrado = true;

                    // Dependendo do tipo de pessoa, cria o objeto e imprime as informações
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

    public void buscaBinaria(File arquivo, int idProcurado, Pessoa pessoa) {
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

    public int getRegistroTamanho() {
        //presumindo que o tamanho é 128, nao sei o tamanho certo xd
        return 128;
    }
}