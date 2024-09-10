import java.io.*;
import java.util.*;

public class SelecaoSubstituicao {

    public static int gerarParticoes(String nome, List<Integer> ids) throws IOException {
        List<Integer> linhas = new ArrayList<>(ids);
        List<Integer> analise = new ArrayList<>();
        List<Integer> particao = new ArrayList<>();
        List<Integer> congelados = new ArrayList<>();
        List<Integer> todasIds = new ArrayList<>(); // Lista para armazenar todos os IDs
        int contador = 0;

        // Iniciar com os primeiros 6 IDs na lista analise
        for (int i = 0; i < 6 && !linhas.isEmpty(); i++) {
            analise.add(linhas.remove(0));
        }

        // Processo de substituição por seleção
        while (!linhas.isEmpty()) {
            while (congelados.size() < 6 && !linhas.isEmpty()) {
                int menor = Integer.MAX_VALUE;
                int menoraux;
                int i;

                if (particao.isEmpty()) {
                    for (i = 0; i < analise.size(); i++) {
                        if (analise.get(i) < menor) {
                            menor = analise.get(i);
                        }
                    }
                    i = analise.indexOf(menor);
                    analise.remove(i);
                    analise.add(i, linhas.remove(0));
                    particao.add(menor);
                } else {
                    for (i = 0; i < analise.size(); i++) {
                        if (analise.get(i) < menor) {
                            menoraux = menor;
                            menor = analise.get(i);
                            if (verifica(menor, particao) == 0) {
                                menor = menoraux;
                                if (!congelados.contains(analise.get(i))) {
                                    congelados.add(analise.get(i));
                                }
                            }
                        }
                    }
                    if (linhas.size() > 1 && verifica(menor, particao) == 1 && congelados.size() < 6) {
                        i = analise.indexOf(menor);
                        if (i != -1) {
                            analise.remove(i);
                            analise.add(i, linhas.remove(0));
                            particao.add(menor);
                        }
                    } else if (linhas.size() == 1 && !analise.isEmpty()) {
                        analise.add(linhas.remove(0));
                        Collections.sort(analise);
                        particao.addAll(analise);
                    }
                }
            }

            // Escrever a partição em arquivo e adicionar à lista de todos os IDs
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(nome + "_particao_" + ++contador + ".txt"));
            for (int id : particao) {
                logWriter.write(id + "\n");
                todasIds.add(id); // Adicionar todos os IDs da partição à lista
            }
            logWriter.close();
            particao.clear();
            congelados.clear();
        }
        return contador;
    }

    public static int verifica(int menor, List<Integer> particao) {
        return menor > particao.get(particao.size() - 1) ? 1 : 0;
    }
}
