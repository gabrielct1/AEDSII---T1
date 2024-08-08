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

    @Override
    public void imprimeInfo() {
        System.out.println("Nome: " + super.getNome());
        System.out.println("ID: " + super.getId());
        System.out.println("Data de Nascimento: " + this.getDataNascimento());
        System.out.println("Telefone: " + this.getTelefone());
    }
}
