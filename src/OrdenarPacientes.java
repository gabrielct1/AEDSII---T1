import java.io.*;
import java.util.*;

public class OrdenarPacientes {

    private static final String CAMINHO_ORIGINAL = "C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\pacientes.dat";
    private static final String CAMINHO_ORDENADO = "C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\pacientes_ordenados.dat";

    public static void ordenarPacientes() {
        // Lista para armazenar registros congelados
        List<Paciente> congelados = new ArrayList<>();
        // Lista para armazenar registros para processamento
        List<Paciente> memoria = new ArrayList<>();

        // Armazenamento e processamento dos dados
        try (DataInputStream dis = new DataInputStream(new FileInputStream(CAMINHO_ORIGINAL))) {
            File arquivoTemporario = File.createTempFile("pacientes_temp", ".dat");
            arquivoTemporario.deleteOnExit(); // Garante que o arquivo temporário será excluído ao final

            boolean temMaisRegistros;
            do {
                memoria.clear();
                congelados.clear();

                // Passo 1: Ler registros até o fim do arquivo ou até o buffer ficar cheio
                temMaisRegistros = false;
                while (true) {
                    try {
                        String nome = dis.readUTF();
                        int id = dis.readInt();
                        String dataNascimento = dis.readUTF();
                        String telefone = dis.readUTF();
                        memoria.add(new Paciente(nome, id, dataNascimento, telefone));
                        temMaisRegistros = true;
                    } catch (EOFException e) {
                        break; // Fim do arquivo
                    }
                }

                if (!temMaisRegistros) {
                    break; // Se não houver mais registros, sair do loop
                }

                // Passo 2-4: Ordenar e escrever registros
                PriorityQueue<Paciente> pq = new PriorityQueue<>(Comparator.comparingInt(Paciente::getId));
                pq.addAll(memoria);

                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(arquivoTemporario))) {
                    // Processar os registros na PriorityQueue
                    while (!pq.isEmpty()) {
                        Paciente menorRegistro = pq.poll();
                        dos.writeUTF(menorRegistro.getNome());
                        dos.writeInt(menorRegistro.getId());
                        dos.writeUTF(menorRegistro.getDataNascimento());
                        dos.writeUTF(menorRegistro.getTelefone());

                        // Passo 4: Substituir o registro gravado pelo próximo registro do arquivo
                        try {
                            String nome = dis.readUTF();
                            int id = dis.readInt();
                            String dataNascimento = dis.readUTF();
                            String telefone = dis.readUTF();
                            Paciente novoRegistro = new Paciente(nome, id, dataNascimento, telefone);
                            if (novoRegistro.getId() < menorRegistro.getId()) {
                                congelados.add(novoRegistro); // Congelar registros com ID menor
                            } else {
                                pq.add(novoRegistro);
                            }
                        } catch (EOFException e) {
                            // Fim do arquivo, não há mais registros para ler
                        }
                    }

                    // Passo 7: Gerenciar partições
                    // Escrever registros congelados no final do arquivo temporário
                    for (Paciente p : congelados) {
                        dos.writeUTF(p.getNome());
                        dos.writeInt(p.getId());
                        dos.writeUTF(p.getDataNascimento());
                        dos.writeUTF(p.getTelefone());
                    }
                }

                // Mover dados do arquivo temporário para o arquivo final
                try (InputStream is = new FileInputStream(arquivoTemporario);
                     OutputStream os = new FileOutputStream(CAMINHO_ORDENADO, true)) { // Append mode
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }

            } while (temMaisRegistros);

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo de pacientes: " + e.getMessage());
        }
    }
}