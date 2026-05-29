package src.model;

public class HonorarioParticular implements CalculadorHonorario {
    @Override
    public double calcularValorFinal(double valorBase) {
        return valorBase;
    }
}