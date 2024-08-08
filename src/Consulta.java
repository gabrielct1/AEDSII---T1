import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Consulta {

    private static int ultimoId = 0;
    private int idConsulta;
    private int pacienteId;
    private int medicoId;
    private String data;
    private String hora;

    public Consulta(int pacienteId, int medicoId, String data, String hora) {
        this.idConsulta = gerarNovoId();
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.data = data;
        this.hora = hora;
    }

    public static int gerarNovoId() {
        return ++ultimoId;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    public void gerarRelatorio(Medico medico, Paciente paciente) {
        String nomeArquivo = "relatorio_consulta_" + idConsulta + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Relatório da Consulta\n");
            writer.write("=======================================\n");
            writer.write("ID da Consulta: " + idConsulta + "\n");
            writer.write("Data: " + data + "\n");
            writer.write("Hora: " + hora + "\n");
            writer.write("\nDados do Paciente\n");
            writer.write("---------------------------------------\n");
            writer.write("ID: " + paciente.getId() + "\n");
            writer.write("Nome: " + paciente.getNome() + "\n");
            writer.write("Data de Nascimento: " + paciente.getDataNascimento() + "\n");
            writer.write("Telefone: " + paciente.getTelefone() + "\n");
            writer.write("---------------------------------------\n");
            writer.write("\nDados do Médico\n");
            writer.write("---------------------------------------\n");
            writer.write("ID: " + medico.getId() + "\n");
            writer.write("Nome: " + medico.getNome() + "\n");
            writer.write("Especialidade: " + medico.getEspecialidade() + "\n");
            writer.write("CRM: " + medico.getCrm() + "\n");
            writer.write("---------------------------------------\n");
            writer.write("RELATÓRIO GERADO.");

            System.out.println("Relatório salvo como: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar relatório: " + e.getMessage());
        }
    }
}
