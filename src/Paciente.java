public class Paciente extends Pessoa{
    private String dataNascimento;
    private String telefone;

    public Paciente(String nome, int id, String dataNascimento, String telefone) {
        super(nome, id);
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }
}
