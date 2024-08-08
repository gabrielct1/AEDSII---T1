public class Pessoa {
    private String nome;
    private int id;

    public Pessoa(String nome, int id) {
        this.id = id;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public int getId() {
        return this.id;
    }
}
