import java.io.*;
import java.util.PriorityQueue;

public class ArvoreVencedoresIntercalacao {

    public static void intercalarParticoes(int numParticoes, String nomeParticoes, String arquivoSaida) throws IOException {
        PriorityQueue<ElementoFluxo> arvoreVencedores = new PriorityQueue<>();

        BufferedReader[] leitores = new BufferedReader[numParticoes];

        // Abrir todos os arquivos de partição
        for (int i = 0; i < numParticoes; i++) {
            leitores[i] = new BufferedReader(new FileReader(nomeParticoes + "_particao_" + (i + 1) + ".txt"));
            String linha = leitores[i].readLine();
            if (linha != null) {
                arvoreVencedores.add(new ElementoFluxo(Integer.parseInt(linha), i));
            }
        }

        // Criar o arquivo de saída com extensão .dat
        BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoSaida + ".dat"));

        // Processo de intercalação
        while (!arvoreVencedores.isEmpty()) {
            ElementoFluxo vencedor = arvoreVencedores.poll();
            escritor.write(vencedor.valor + "\n");

            // Lê o próximo elemento da partição de onde o vencedor saiu
            String novaLinha = leitores[vencedor.particao].readLine();
            if (novaLinha != null) {
                arvoreVencedores.add(new ElementoFluxo(Integer.parseInt(novaLinha), vencedor.particao));
            }
        }

        // Fechar leitores e o escritor
        for (BufferedReader leitor : leitores) {
            leitor.close();
        }
        escritor.close();
    }

    // Classe auxiliar que representa um elemento de um fluxo (partição)
    private static class ElementoFluxo implements Comparable<ElementoFluxo> {
        int valor;     // Valor do elemento
        int particao;  // Qual partição esse valor pertence

        public ElementoFluxo(int valor, int particao) {
            this.valor = valor;
            this.particao = particao;
        }

        @Override
        public int compareTo(ElementoFluxo outro) {
            return Integer.compare(this.valor, outro.valor);
        }
    }
}
