import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CriarBases {

    public static void criarBasesDesordenadas() {
        List<Paciente> pacientes = new ArrayList<>();
        List<Medico> medicos = new ArrayList<>();

        // Adicionar pacientes ao vetor
        for (int i = 1000; i < 1010; i++) {
            Paciente p = new Paciente("Ana", i, "27/10/2003", "98627-9186");
            pacientes.add(p);
        }

        // Adicionar médicos ao vetor
        for (int j = 2000; j < 2010; j++) {
            Medico m = new Medico("Gabriel", j, "Clínico Geral", "23-854.986");
            medicos.add(m);
        }

        // Embaralhar os vetores
        Collections.shuffle(pacientes);
        Collections.shuffle(medicos);
        // Caminhos para os arquivos
        String caminhoPacientes = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\pacientes.dat";
        String caminhoMedicos = "C:\\Users\\gabri\\Documents\\UFOP\\AEDS 2\\AEDSII - Trabalho I\\medicos.dat";

        // Salvar dados dos pacientes
        try (DataOutputStream dosPacientes = new DataOutputStream(new FileOutputStream(caminhoPacientes))) {
            for (Paciente p : pacientes) {
                dosPacientes.writeUTF(p.getNome());
                dosPacientes.writeInt(p.getId());
                dosPacientes.writeUTF(p.getDataNascimento());
                dosPacientes.writeUTF(p.getTelefone());
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo de pacientes: " + e.getMessage());
        }

        // Salvar dados dos médicos
        try (DataOutputStream dosMedicos = new DataOutputStream(new FileOutputStream(caminhoMedicos))) {
            for (Medico m : medicos) {
                dosMedicos.writeUTF(m.getNome());
                dosMedicos.writeInt(m.getId());
                dosMedicos.writeUTF(m.getEspecialidade());
                dosMedicos.writeUTF(m.getCrm());
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular o arquivo de médicos: " + e.getMessage());
        }
    }
}
