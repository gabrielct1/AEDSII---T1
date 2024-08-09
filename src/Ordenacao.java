import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;

public class Ordenacao {

    public static void selectionSort(String arquivo, int tamRegistro) {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "rw")) {
            long n = raf.length() / tamRegistro;

            for (int i = 0; i < n - 1; i++) {
                long minIndex = i;
                int minId = leId(raf, i, tamRegistro);

                for (int j = i + 1; j < n; j++) {
                    int idAtual = leId(raf, j, tamRegistro);
                    if (idAtual < minId) {
                        minIndex = j;
                        minId = idAtual;
                    }
                }

                if (minIndex != i) {
                    trocaRegistros(raf, i, minIndex, tamRegistro);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e);
        }
    }

    private static int leId(RandomAccessFile raf, long index, int tamRegistro) throws IOException {
        raf.seek(index * tamRegistro + 4);
        return raf.readInt();
    }

    private static void trocaRegistros(RandomAccessFile raf, long index1, long index2, int tamRegistro) throws IOException {
        byte[] registro1 = new byte[tamRegistro];
        byte[] registro2 = new byte[tamRegistro];

        // Ler registros
        raf.seek(index1 * tamRegistro);
        raf.readFully(registro1);

        raf.seek(index2 * tamRegistro);
        raf.readFully(registro2);

        // Trocar registros
        raf.seek(index1 * tamRegistro);
        raf.write(registro2);

        raf.seek(index2 * tamRegistro);
        raf.write(registro1);
    }
}
