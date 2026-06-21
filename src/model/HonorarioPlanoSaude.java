package src.model;

public class HonorarioPlanoSaude implements CalculadorHonorario {
    @Override
    public double calcularValorFinal(double valorBase) {
        return valorBase * 0.8; 
    }
}