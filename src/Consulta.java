import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Consulta {

    private int idConsulta;
    private int pacienteId;
    private int medicoId;
    private String data;
    private String hora;

    public Consulta(int idConsulta, int pacienteId, int medicoId, String data, String hora) {
        this.idConsulta = idConsulta;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.data = data;
        this.hora = hora;
    }

    public int setIdConsulta() {
        return idConsulta;
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

    public  int calcularTamanhoRegistro(){
        return 4 + 4 + 4 + 10 + 5;
    }
}