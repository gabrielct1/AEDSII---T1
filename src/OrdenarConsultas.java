import java.io.*;
import java.util.*;

public class OrdenarConsultas {
    private static final String CAMINHO_ORIGINAL = "C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\consultas.dat";
    private static final String CAMINHO_ORDENADO = "C:\\Users\\Teknisa\\IdeaProjects\\AEDSII - Trabalho I\\consultas_ordenadas.dat";

    public static void ordenarConsultas() {
        // Lista para armazenar registros congelados
        List<Consulta> congelados = new ArrayList<>();
        // Lista para armazenar registros para processamento
        List<Consulta> memoria = new ArrayList<>();

        // Armazenamento e processamento dos dados
        try (DataInputStream dis = new DataInputStream(new FileInputStream(CAMINHO_ORIGINAL))) {
            File arquivoTemporario = File.createTempFile("consultas_temp", ".dat");
            arquivoTemporario.deleteOnExit(); // Garante que o arquivo temporário será excluído ao final

            boolean temMaisRegistros;
            do {
                memoria.clear();
                congelados.clear();

                // Passo 1: Ler registros até o fim do arquivo ou até o buffer ficar cheio
                temMaisRegistros = false;
                while (true) {
                    try {
                        int idConsulta = dis.readInt();
                        int pacienteId = dis.readInt();
                        int medicoId = dis.readInt();
                        String data = dis.readUTF();
                        String hora = dis.readUTF();
                        memoria.add(new Consulta(idConsulta, pacienteId, medicoId, data, hora));
                        temMaisRegistros = true;
                    } catch (EOFException e) {
                        break; // Fim do arquivo
                    }
                }

                if (!temMaisRegistros) {
                    break; // Se não houver mais registros, sair do loop
                }

                // Passo 2-4: Ordenar e escrever registros
                PriorityQueue<Consulta> pq = new PriorityQueue<>(Comparator.comparingInt(Consulta::getIdConsulta));
                pq.addAll(memoria);

                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(arquivoTemporario))) {
                    // Processar os registros na PriorityQueue
                    while (!pq.isEmpty()) {
                        Consulta menorRegistro = pq.poll();
                        dos.writeInt(menorRegistro.getIdConsulta());
                        dos.writeInt(menorRegistro.getPacienteId());
                        dos.writeInt(menorRegistro.getMedicoId());
                        dos.writeUTF(menorRegistro.getData());
                        dos.writeUTF(menorRegistro.getHora());

                        // Passo 4: Substituir o registro gravado pelo próximo registro do arquivo
                        try {
                            int idConsulta = dis.readInt();
                            int pacienteId = dis.readInt();
                            int medicoId = dis.readInt();
                            String data = dis.readUTF();
                            String hora = dis.readUTF();
                            Consulta novoRegistro = new Consulta(idConsulta, pacienteId, medicoId, data, hora);
                            if (novoRegistro.getIdConsulta() < menorRegistro.getIdConsulta()) {
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
                    for (Consulta c : congelados) {
                        dos.writeInt(c.getIdConsulta());
                        dos.writeInt(c.getPacienteId());
                        dos.writeInt(c.getMedicoId());
                        dos.writeUTF(c.getData());
                        dos.writeUTF(c.getHora());
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
            System.err.println("Erro ao processar o arquivo de consultas: " + e.getMessage());
        }
    }
}
