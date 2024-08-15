import java.io.*;

public class QuickSortMedicos {

    private static final int TAMANHO_NOME = 50;
    private static final int TAMANHO_ID = 4; // int
    private static final int TAMANHO_ESPECIALIDADE = 25;
    private static final int TAMANHO_CRM = 7;
    private static final int TAMANHO_REGISTRO_MEDICO = TAMANHO_NOME + TAMANHO_ID + TAMANHO_ESPECIALIDADE + TAMANHO_CRM;

    public static void ordenarDisco(String arquivoEntrada, String arquivoSaida) throws IOException {
        String arquivoTemp = "medicos_temp.dat";

        try (RandomAccessFile rafEntrada = new RandomAccessFile(arquivoEntrada, "r");
             RandomAccessFile rafTemp = new RandomAccessFile(arquivoTemp, "rw")) {

            // Copiar dados do arquivo original para o arquivo temporário
            byte[] buffer = new byte[TAMANHO_REGISTRO_MEDICO];
            while (rafEntrada.read(buffer) != -1) {
                rafTemp.write(buffer);
            }

            // Ordenar o arquivo temporário
            System.out.println("Ordenando arquivo de disco.");
            int quantidade = (int) (rafTemp.length() / TAMANHO_REGISTRO_MEDICO);
            quickSortDisco(rafTemp, 0, quantidade - 1);

            // Copiar dados do arquivo temporário para o arquivo de saída
            try (RandomAccessFile rafSaida = new RandomAccessFile(arquivoSaida, "rw")) {
                rafTemp.seek(0);
                while (rafTemp.read(buffer) != -1) {
                    rafSaida.write(buffer);
                }
            }

            System.out.println("Lista ordenada e gravada em " + arquivoSaida);
        }

        // Opcional: Excluir o arquivo temporário após a cópia
        File tempFile = new File(arquivoTemp);
        if (tempFile.delete()) {
            System.out.println("Arquivo temporário excluído.");
        } else {
            System.out.println("Não foi possível excluir o arquivo temporário.");
        }
    }

    private static void quickSortDisco(RandomAccessFile raf, int esquerda, int direita) throws IOException {
        if (esquerda < direita) {
            int idPivo = obterId(raf, (esquerda + direita) / 2);
            int i = esquerda;
            int j = direita;

            while (i <= j) {
                while (obterId(raf, i) < idPivo && i < direita) i++;
                while (obterId(raf, j) > idPivo && j > esquerda) j--;

                if (i <= j) {
                    trocarTodosCampos(raf, i, j);
                    i++;
                    j--;
                }
            }

            if (esquerda < j) quickSortDisco(raf, esquerda, j);
            if (i < direita) quickSortDisco(raf, i, direita);
        }
    }

    private static void trocarTodosCampos(RandomAccessFile raf, long i, long j) throws IOException {
        byte[] registroI = new byte[TAMANHO_REGISTRO_MEDICO];
        byte[] registroJ = new byte[TAMANHO_REGISTRO_MEDICO];

        // Ler o registro na posição i
        raf.seek(i * TAMANHO_REGISTRO_MEDICO);
        raf.readFully(registroI);

        // Ler o registro na posição j
        raf.seek(j * TAMANHO_REGISTRO_MEDICO);
        raf.readFully(registroJ);

        // Escrever o registro na posição j
        raf.seek(j * TAMANHO_REGISTRO_MEDICO);
        raf.write(registroI);

        // Escrever o registro na posição i
        raf.seek(i * TAMANHO_REGISTRO_MEDICO);
        raf.write(registroJ);
    }

    private static int obterId(RandomAccessFile raf, long registro) throws IOException {
        raf.seek(registro * TAMANHO_REGISTRO_MEDICO + TAMANHO_NOME); // Posiciona-se no campo ID
        return raf.readInt(); // Lê o inteiro diretamente
    }
}
