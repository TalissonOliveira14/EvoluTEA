package src.model;


public class Responsavel extends Pessoa {

    
    private String email;
    private String grauParentesco;

    // Construtor
    public Responsavel(int id, String nome, String cpf, String telefone, String email, String grauParentesco) {
        
        super(id, nome, cpf, telefone);
        
        
        this.email = email;
        this.grauParentesco = grauParentesco;
    }

    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }
}