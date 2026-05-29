package src.model;

import java.time.LocalDate;

public class Paciente extends Pessoa {
    private LocalDate dataDiagnosticoTEA;
    private String historicoClinico;
    private String necessidadeSuporte;
    private int idResponsavel; // ✨ Nova ligação (Chave Estrangeira)

    public Paciente(int id, String nome, String cpf, String telefone, LocalDate dataDiagnosticoTEA, String historicoClinico, String necessidadeSuporte, int idResponsavel) {
        super(id, nome, cpf, telefone);
        this.dataDiagnosticoTEA = dataDiagnosticoTEA;
        this.historicoClinico = historicoClinico;
        this.necessidadeSuporte = necessidadeSuporte;
        this.idResponsavel = idResponsavel;
    }

    public LocalDate getDataDiagnosticoTEA() {
        return dataDiagnosticoTEA;
    }

    public void setDataDiagnosticoTEA(LocalDate dataDiagnosticoTEA) {
        this.dataDiagnosticoTEA = dataDiagnosticoTEA;
    }

    public String getHistoricoClinico() {
        return historicoClinico;
    }

    public void setHistoricoClinico(String historicoClinico) {
        this.historicoClinico = historicoClinico;
    }

    public String getNecessidadeSuporte() {
        return necessidadeSuporte;
    }

    public void setNecessidadeSuporte(String necessidadeSuporte) {
        this.necessidadeSuporte = necessidadeSuporte;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }
}