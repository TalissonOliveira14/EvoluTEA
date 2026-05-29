package src.model;

// A palavra 'abstract' garante que esta classe só sirva como molde para a herança
public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    // Construtor para inicializar os dados comuns a todos
    public Pessoa(int id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // Getters e Setters (Necessários para o encapsulamento dos dados)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}