package src.model;

// Profissional herda ID, Nome, CPF e Telefone de Pessoa
public class Profissional extends Pessoa {

    // Atributos exclusivos do Profissional de Saúde/Educação
    private String registroProfissional; // Ex: CRP, CRM, CREFITO
    private String especialidade;        // Ex: Psicólogo, Fonoaudiólogo, Psicopedagogo

    // Construtor
    public Profissional(int id, String nome, String cpf, String telefone, 
                        String registroProfissional, String especialidade) {
        // Envia as informações comuns para a classe mãe
        super(id, nome, cpf, telefone);
        
        // Inicializa as informações exclusivas do profissional
        this.registroProfissional = registroProfissional;
        this.especialidade = especialidade;
    }

    // Getters e Setters específicos
    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}