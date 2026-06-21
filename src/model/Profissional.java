package src.model;


public class Profissional extends Pessoa {

    
    private String registroProfissional; 
    private String especialidade;        

    
    public Profissional(int id, String nome, String cpf, String telefone, 
                        String registroProfissional, String especialidade) {
        
        super(id, nome, cpf, telefone);
        
        
        this.registroProfissional = registroProfissional;
        this.especialidade = especialidade;
    }

    
    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}