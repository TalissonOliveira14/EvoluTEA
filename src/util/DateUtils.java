package src.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateUtils {
    // Usaremos um formatador padrão para evitar erros de leitura em diferentes terminais
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate lerData(Scanner scanner, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + " (dd/MM/yyyy): ");
                String entrada = scanner.nextLine().trim(); // .trim() remove espaços extras
                
                LocalDate data = LocalDate.parse(entrada, FORMATTER);

                if (data.isAfter(LocalDate.now())) {
                    System.out.println("❌ Erro: A data não pode ser futura!");
                    continue;
                }
                return data;
            } catch (DateTimeParseException e) {
                System.out.println("❌ Erro: Formato inválido ou data inexistente. Tente o formato dd/MM/yyyy.");
            }
        }
    }

    public static LocalDate parse(String dataString) {
        return LocalDate.parse(dataString.trim(), FORMATTER);
    }

    public static String formatar(LocalDate data) {
        return data.format(FORMATTER);
    }
}