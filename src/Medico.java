public class Medico extends Pessoa {

    private String especialidade;
    private String crm;

    public Medico(String nome, int id, String especialidade, String crm) {
        super(nome, id);
        this.especialidade = especialidade;
        this.crm = crm;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public String getCrm() {
        return crm;
    }

    public int calcularTamanhoRegistro() {
        return 50 + 4 + 25 + 7;
    }
}
