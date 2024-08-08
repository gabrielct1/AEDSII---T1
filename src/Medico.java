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

    public void imprimeInfo() {
        System.out.println("Nome: " + super.getNome());
        System.out.println("ID: " + super.getId());
        System.out.println("Especialidade: " + this.getEspecialidade());
        System.out.println("CRM: " + this.getCrm());
    }
}
