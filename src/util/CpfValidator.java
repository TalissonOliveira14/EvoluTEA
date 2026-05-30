package src.util;

public class CpfValidator {

    public static boolean isCpfValido(String cpf) {
        // Remove tudo que não é número
        cpf = cpf.replaceAll("\\D", "");

        // CPF deve ter 11 dígitos e não pode ser uma sequência repetida (ex: 111.111.111-11)
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            // Cálculo do primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;

            // Cálculo do segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;

            // Compara com os dígitos informados
            return (digito1 == (cpf.charAt(9) - '0')) && (digito2 == (cpf.charAt(10) - '0'));
        } catch (Exception e) {
            return false;
        }
    }
}