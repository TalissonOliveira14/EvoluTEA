package src.util;

public class RegrasClinicas {
    public static String getDificuldadePelaEspecialidade(String especialidade) {
        if (especialidade.equals("Fonoaudiólogo")) return "Fala";
        if (especialidade.equals("Psicólogo")) return "Comportamento";
        if (especialidade.equals("Terapeuta Ocupacional")) return "Motor";
        if (especialidade.equals("Pedagogo")) return "Socialização";
        return "Outros";
    }
}