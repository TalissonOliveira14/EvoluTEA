package src.model;

import src.exception.EstadoInvalidoException;

public class Sessao {
    private int id;
    private int idPaciente;
    private int idProfissional;
    private String data;
    private double valorBase;
    private String tipoAtendimento; // PARTICULAR ou PLANO
    private String estado; // AGENDADA, REALIZADA, EVOLUIDA, CANCELADA

    public Sessao(int id, int idPaciente, int idProfissional, String data, double valorBase, String tipoAtendimento) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idProfissional = idProfissional;
        this.data = data;
        this.valorBase = valorBase;
        this.tipoAtendimento = tipoAtendimento.toUpperCase();
        this.estado = "AGENDADA"; // Todo agendamento nasce como AGENDADA
    }

    public void transicionarPara(String novoEstado) throws EstadoInvalidoException {
        novoEstado = novoEstado.toUpperCase();
        
        if (this.estado.equals("AGENDADA")) {
            if (!novoEstado.equals("REALIZADA") && !novoEstado.equals("CANCELADA")) {
                throw new EstadoInvalidoException("Uma sessão AGENDADA só pode passar para REALIZADA ou CANCELADA!");
            }
        } else if (this.estado.equals("REALIZADA")) {
            if (!novoEstado.equals("EVOLUIDA")) {
                throw new EstadoInvalidoException("Uma sessão REALIZADA só pode passar para EVOLUIDA (Inserção de evolução clínica)!");
            }
        } else if (this.estado.equals("EVOLUIDA") || this.estado.equals("CANCELADA")) {
            throw new EstadoInvalidoException("Sessões nos estados EVOLUIDA ou CANCELADA são finais e não podem ser alteradas.");
        }
        
        this.estado = novoEstado;
    }

    public double getValorLiquidoProfissional() {
        CalculadorHonorario calculador;
        if (this.tipoAtendimento.equals("PLANO")) {
            calculador = new HonorarioPlanoSaude();
        } else {
            calculador = new HonorarioParticular();
        }
        return calculador.calcularValorFinal(this.valorBase);
    }

    // Getters e Setters
    public int getId() { return id; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdProfissional() { return idProfissional; }
    public String getData() { return data; }
    public double getValorBase() { return valorBase; }
    public String getTipoAtendimento() { return tipoAtendimento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}