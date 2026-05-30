package src.model.estados;
import src.model.Sessao;

public class EstadoRealizada implements EstadoSessao {
    public void proximo(Sessao sessao) {
        System.out.println("A sessão já foi finalizada!");
    }
    public String getNomeEstado() { return "REALIZADA"; }
}
