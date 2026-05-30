package src.service;

import src.model.Paciente;
import src.model.Sessao;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {

    // Relatório usando Stream API para agrupar pacientes por nível de suporte
    public static void exibirPacientesPorNivel(List<Paciente> pacientes) {
        System.out.println("\n--- RELATÓRIO: PACIENTES POR NÍVEL DE SUPORTE ---");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        Map<String, Long> contagem = pacientes.stream()
                .collect(Collectors.groupingBy(Paciente::getNecessidadeSuporte, Collectors.counting()));
        
        contagem.forEach((nivel, total) -> 
            System.out.println("Nível " + nivel + ": " + total + " paciente(s)"));
    }

    // Relatório usando Stream API para somar valores de sessões realizadas
    public static void exibirFaturamentoSessoes(List<Sessao> sessoes) {
        System.out.println("\n--- RELATÓRIO: FATURAMENTO DE SESSÕES ---");
        double total = sessoes.stream()
                .filter(s -> "REALIZADA".equalsIgnoreCase(s.getNomeEstado()))
                .mapToDouble(Sessao::getValorBase)
                .sum();
        
        System.out.printf("Valor total arrecadado (Sessões Realizadas): R$ %.2f%n", total);
    }
}