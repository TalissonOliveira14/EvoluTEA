package src.model;

// Responsavel estende Pessoa, herdando ID, Nome, CPF e Telefone
public class Responsavel extends Pessoa {

    // Atributos exclusivos do Responsável
    private String email;
    private String grauParentesco; // Ex: Pai, Mãe, Tutor Legal, Avó

    // Construtor
    public Responsavel(int id, String nome, String cpf, String telefone, String email, String grauParentesco) {
        // Envia os dados básicos para a classe mãe (Pessoa)
        super(id, nome, cpf, telefone);
        
        // Inicializa os dados específicos do Responsável
        this.email = email;
        this.grauParentesco = grauParentesco;
    }

    // Getters e Setters específicos
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }
}