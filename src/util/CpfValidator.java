package src.util;

import src.exception.CpfInvalidoException;

public class CpfValidator {

    public static boolean isCpfValido(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;

            return (digito1 == (cpf.charAt(9) - '0')) && (digito2 == (cpf.charAt(10) - '0'));
        } catch (Exception e) {
            return false;
        }
    }

   
    public static void validar(String cpf) {
        if (!isCpfValido(cpf)) {
            throw new CpfInvalidoException("O CPF informado (" + cpf + ") é inválido.");
        }
    }
}