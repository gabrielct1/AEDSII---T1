import java.io.*;

class TabelaHashDisco {
    private static final String ARQUIVO_TABELA = "tabela_hash.dat";
    private static final String ARQUIVO_LISTAS = "listas_encadeadas.dat";
    private int tamanho;

    // Construtor que inicializa a tabela hash
    public TabelaHashDisco(int tamanho) {
        this.tamanho = tamanho;
        inicializarArquivos();
    }

    // Inicializa os arquivos da tabela hash e das listas encadeadas
    private void inicializarArquivos() {
        try (RandomAccessFile tabelaFile = new RandomAccessFile(ARQUIVO_TABELA, "rw");
             RandomAccessFile listasFile = new RandomAccessFile(ARQUIVO_LISTAS, "rw")) {
            // Preenche a tabela com -1 (vazia)
            for (int i = 0; i < tamanho; i++) {
                tabelaFile.writeInt(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função hash que retorna a posição na tabela
    private int hash(int id) {
        return id % tamanho;
    }

    // Insere um paciente na tabela hash
    public void inserir(PacienteS paciente) {
        int posicao = hash(paciente.id);
        System.out.println("Tentando inserir paciente: ID = " + paciente.id + ", nome = " + paciente.nome);

        try (RandomAccessFile tabelaFile = new RandomAccessFile(ARQUIVO_TABELA, "rw");
             RandomAccessFile listasFile = new RandomAccessFile(ARQUIVO_LISTAS, "rw")) {

            // Lê o primeiro índice para verificar se já existe um paciente nesse índice
            tabelaFile.seek(posicao * 4);
            int primeiroIndice = tabelaFile.readInt();
            System.out.println("Posição Hash: " + posicao + ", Primeiro índice: " + primeiroIndice);

            // Serializa o paciente em um array de bytes
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteOut);
            oos.writeObject(paciente);
            oos.flush();
            byte[] bytesPaciente = byteOut.toByteArray();

            // Escreve o paciente no arquivo de listas encadeadas
            long novaPosicao = listasFile.length();
            listasFile.seek(novaPosicao);
            listasFile.writeInt(bytesPaciente.length); // Tamanho dos dados do paciente
            listasFile.write(bytesPaciente); // Dados do paciente
            listasFile.writeInt(-1); // Próximo índice (inicialmente -1)

            // Atualiza a tabela hash
            if (primeiroIndice == -1) {
                tabelaFile.seek(posicao * 4);
                tabelaFile.writeInt((int) novaPosicao); // Insere o novo índice na tabela
                System.out.println("Atualizando tabela hash, nova posição: " + novaPosicao);
            } else {
                // Navega até o final da lista encadeada
                long ultimaPosicao = primeiroIndice;
                while (true) {
                    listasFile.seek(ultimaPosicao);
                    int tamanho = listasFile.readInt();
                    listasFile.skipBytes(tamanho);
                    int proximoIndice = listasFile.readInt();

                    if (proximoIndice == -1) {
                        listasFile.seek(ultimaPosicao + 4 + tamanho);
                        listasFile.writeInt((int) novaPosicao); // Atualiza o próximo índice
                        System.out.println("Atualizando ponteiro de colisão, nova posição: " + novaPosicao);
                        break;
                    }
                    ultimaPosicao = proximoIndice;
                }
            }

            System.out.println("Paciente inserido com sucesso: ID = " + paciente.id + ", Nome = " + paciente.nome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exibe a tabela hash
    public void exibirTabelaHash() {
        try (RandomAccessFile tabelaFile = new RandomAccessFile(ARQUIVO_TABELA, "r");
             RandomAccessFile listasFile = new RandomAccessFile(ARQUIVO_LISTAS, "r")) {

            System.out.println("Tabela Hash:");
            for (int i = 0; i < tamanho; i++) {
                tabelaFile.seek(i * 4);
                int primeiroIndice = tabelaFile.readInt();
                System.out.print("Índice " + i + ": ");

                if (primeiroIndice == -1) {
                    System.out.println("vazio");
                } else {
                    // Percorre a lista encadeada
                    while (primeiroIndice != -1) {
                        listasFile.seek(primeiroIndice);
                        int tamanhoPaciente = listasFile.readInt();
                        byte[] bytesPaciente = new byte[tamanhoPaciente];
                        listasFile.readFully(bytesPaciente);

                        // Desserializa o paciente
                        PacienteS paciente = (PacienteS) new ObjectInputStream(new ByteArrayInputStream(bytesPaciente)).readObject();
                        System.out.print("{" + paciente.id + ", " + paciente.nome + "} -> ");
                        primeiroIndice = listasFile.readInt(); // Lê o próximo índice
                    }
                    System.out.println("null");
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
