package src.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private LocalDate dataDiagnosticoTEA;
    private String historicoClinico;
    private boolean temLaudo; 
    private List<String> dificuldades; 
    private int idResponsavel;

    // Construtor completo
    public Paciente(int id, String nome, String cpf, String telefone, LocalDate dataDiagnosticoTEA, 
                    String historicoClinico, boolean temLaudo, List<String> dificuldades, int idResponsavel) {
        super(id, nome, cpf, telefone);
        this.dataDiagnosticoTEA = dataDiagnosticoTEA;
        this.historicoClinico = historicoClinico;
        this.temLaudo = temLaudo;
        // 2
        this.dificuldades = (dificuldades != null) ? dificuldades : new ArrayList<>();
        this.idResponsavel = idResponsavel;
    }

    // Tratamento aonde se plica a uma triagem
    public String getStatusTriagem() {
        return temLaudo ? "Tratamento (Com Laudo)" : "Investigação (Sem Laudo)";
    }

    // Getters e Setters
    public LocalDate getDataDiagnosticoTEA() { return dataDiagnosticoTEA; }
    public void setDataDiagnosticoTEA(LocalDate dataDiagnosticoTEA) { this.dataDiagnosticoTEA = dataDiagnosticoTEA; }

    public String getHistoricoClinico() { return historicoClinico; }
    public void setHistoricoClinico(String historicoClinico) { this.historicoClinico = historicoClinico; }

    public boolean isTemLaudo() { return temLaudo; }
    public void setTemLaudo(boolean temLaudo) { this.temLaudo = temLaudo; }

    public List<String> getDificuldades() { return dificuldades; }
    public void setDificuldades(List<String> dificuldades) { this.dificuldades = dificuldades; }

    public int getIdResponsavel() { return idResponsavel; }
    public void setIdResponsavel(int idResponsavel) { this.idResponsavel = idResponsavel; }
}