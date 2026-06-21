package src.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate lerData(Scanner scanner, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + " (dd/MM/yyyy): ");
                String entrada = scanner.nextLine();
                LocalDate data = LocalDate.parse(entrada, FORMATTER);

                // Regra de exemplo: Data não pode ser futura
                if (data.isAfter(LocalDate.now())) {
                    System.out.println("❌ Erro: A data não pode ser futura!");
                    continue;
                }
                return data; // Se chegou aqui, a data é válida e segura
            } catch (DateTimeParseException e) {
                System.out.println("❌ Erro: Formato inválido ou data inexistente. Tente novamente.");
            }
        }
    }

    public static LocalDate parse(String dataString) {
    return LocalDate.parse(dataString, FORMATTER);
}

    public static String formatar(LocalDate data) {
        return data.format(FORMATTER);
    }
}