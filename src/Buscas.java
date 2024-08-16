import java.io.*;
import java.nio.charset.StandardCharsets;

public class Buscas {

    private static final int TAMANHO_NOME = 50;
    private static final int TAMANHO_ID = 4; // int
    private static final int TAMANHO_DATA = 10;
    private static final int TAMANHO_TELEFONE = 12;

    private static final int TAMANHO_ESPECIALIDADE = 25;
    private static final int TAMANHO_CRM = 7;

    private static final int TAMANHO_HORA = 5; // "HH:MM" é sempre 5 bytes

    public static void buscaSequencialPacientes(String arquivo, int idProcurado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            boolean encontrado = false;

            while (raf.getFilePointer() < raf.length()) {
                String nome = lerString(raf, 50);
                int id = raf.readInt();
                String dataNascimento = lerString(raf, 10);
                String telefone = lerString(raf, 12);

                if (id == idProcurado) {
                    encontrado = true;
                    exibirDadosRegistro(arquivo, nome, id, dataNascimento, telefone);
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Paciente com ID " + idProcurado + " não encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void buscaSequencialMedicos(String arquivo, int idProcurado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            boolean encontrado = false;

            while (raf.getFilePointer() < raf.length()) {
                String nome = lerString(raf, 50);
                int id = raf.readInt();
                String especialidade = lerString(raf, 25);
                String crm = lerString(raf, 7);

                if (id == idProcurado) {
                    encontrado = true;
                    exibirDadosRegistro(arquivo, nome, id, especialidade, crm);
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Médico com ID " + idProcurado + " não encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void buscaSequencialConsulta(String arquivo, int idProcurado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            boolean encontrado = false;

            while (raf.getFilePointer() < raf.length()) {
                int idConsulta = raf.readInt();
                int pacienteId = raf.readInt();
                int medicoId = raf.readInt();
                String data = lerString(raf, 10);
                String hora = lerString(raf, 5);

                if (idConsulta == idProcurado) {
                    encontrado = true;

                    exibirDadosConsulta(idConsulta, pacienteId, medicoId, data, hora);
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Consulta com ID " + idProcurado + " não encontrada.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void buscaBinariaPacientes(String arquivo, int idConsultaBuscado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            long tamanhoRegistro = 76; // Cada registro tem 76 bytes.
            long numeroRegistros = raf.length() / tamanhoRegistro;
            long inicio = 0;
            long fim = numeroRegistros - 1;

            while (inicio <= fim) {
                long meio = (inicio + fim) / 2;
                raf.seek(meio * tamanhoRegistro);

                String nome = lerString(raf, TAMANHO_NOME);
                int idConsulta = raf.readInt();
                String dataNascimento = lerString(raf, TAMANHO_DATA);
                String telefone = lerString(raf, TAMANHO_TELEFONE);

                if (idConsulta == idConsultaBuscado) {
                    exibirDadosRegistro(arquivo, nome, idConsulta, dataNascimento, telefone);
                    return;
                } else if (idConsulta < idConsultaBuscado) {
                    inicio = meio + 1;
                } else {
                    fim = meio - 1;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar o paciente: " + e.getMessage());
        }
        System.out.println("Paciente com o ID " + idConsultaBuscado + " não encontrado.");
    }

    public static void buscaBinariaMedicos(String arquivo, int idConsultaBuscado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo,   "r")) {
            long tamanhoRegistro = 86; // Cada registro tem 86 bytes.
            long numeroRegistros = raf.length() / tamanhoRegistro;
            long inicio = 0;
            long fim = numeroRegistros - 1;

            while (inicio <= fim) {
                long meio = (inicio + fim) / 2;
                raf.seek(meio * tamanhoRegistro);

                String nome = lerString(raf, TAMANHO_NOME);
                int idConsulta = raf.readInt();
                String especialidade = lerString(raf, TAMANHO_ESPECIALIDADE);
                String crm = lerString(raf, TAMANHO_CRM);

                if (idConsulta == idConsultaBuscado) {
                    exibirDadosRegistro(arquivo, nome, idConsulta, especialidade, crm);
                    return;
                } else if (idConsulta < idConsultaBuscado) {
                    inicio = meio + 1;
                } else {
                    fim = meio - 1;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar o médico: " + e.getMessage());
        }
        System.out.println("Médico com o ID " + idConsultaBuscado + " não encontrado.");
    }

    public static void buscaBinariaConsulta(String arquivo, int idConsultaBuscado) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            long tamanhoRegistro = 27; // Cada registro tem 27 bytes.
            long numeroRegistros = raf.length() / tamanhoRegistro;
            long inicio = 0;
            long fim = numeroRegistros - 1;

            while (inicio <= fim) {
                long meio = (inicio + fim) / 2;
                raf.seek(meio * tamanhoRegistro);

                int idConsulta = raf.readInt();
                int pacienteId = raf.readInt();
                int medicoId = raf.readInt();
                String data = lerString(raf, TAMANHO_DATA);
                String hora = lerString(raf, TAMANHO_HORA);

                if (idConsulta == idConsultaBuscado) {
                    exibirDadosConsulta(idConsulta, pacienteId, medicoId, data, hora);
                    return;
                } else if (idConsulta < idConsultaBuscado) {
                    inicio = meio + 1;
                } else {
                    fim = meio - 1;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar a consulta: " + e.getMessage());
        }
        System.out.println("Consulta não encontrada.");
    }

    private static String lerString(RandomAccessFile raf, int tamanho) throws IOException {
        byte[] bytes = new byte[tamanho];
        raf.readFully(bytes);
        // Convertendo os bytes lidos em uma string
        return new String(bytes, "UTF-8");
    }

    public static void exibirDadosRegistro(String arquivo, String nome, int id, String especialidadeOuDataNascimento, String crmOuTelefone) {
        if (arquivo.contains("pacientes")) {
            System.out.println("Nome: " + nome.trim());
            System.out.println("ID: " + id);
            System.out.println("Data de Nascimento: " + especialidadeOuDataNascimento.trim());
            System.out.println("Telefone: " + crmOuTelefone.trim());

        } else if (arquivo.contains("medicos")) {
            System.out.println("Nome: " + nome);
            System.out.println("ID: " + id);
            System.out.println("Especialidade: " + especialidadeOuDataNascimento);
            System.out.println("CRM: " + crmOuTelefone);
        }
    }

    public static void exibirDadosConsulta(int idConsulta, int pacienteId, int medicoId, String data, String hora) {
        System.out.println("ID da consulta: " + idConsulta);
        System.out.println("ID do paciente: " + pacienteId);
        System.out.println("ID do médico: " + medicoId);
        System.out.println("Data da consulta: " + data);
        System.out.println("Hora: " + hora);
    }
}
