package src.model;

import src.model.estados.*;
import src.exception.EstadoInvalidoException; // Import necessário!

public class Sessao {
    private int id;
    private int idPaciente;
    private int idProfissional;
    private String data;
    private double valorBase;
    private String tipoAtendimento;
    private EstadoSessao estado; 

    public Sessao(int id, int idPaciente, int idProfissional, String data, double valorBase, String tipoAtendimento) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idProfissional = idProfissional;
        this.data = data;
        this.valorBase = valorBase;
        this.tipoAtendimento = tipoAtendimento.toUpperCase();
        this.estado = new EstadoAgendada(); 
    }

    public void setEstado(EstadoSessao estado) { this.estado = estado; }
    
    
    public void avancarFluxo() throws EstadoInvalidoException {
        this.estado.proximo(this); 
    }

    public String getNomeEstado() {
        return this.estado.getNomeEstado();
    }

    public int getId() { return id; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdProfissional() { return idProfissional; }
    public String getData() { return data; }
    public double getValorBase() { return valorBase; }
    public String getTipoAtendimento() { return tipoAtendimento; }
    public double getValorLiquidoProfissional() {
        return this.valorBase * 0.7;
    }
}