package src.model.estados;
import src.model.Sessao;

public class EstadoAgendada implements EstadoSessao {
    public void proximo(Sessao sessao) {
        sessao.setEstado(new EstadoRealizada());
    }
    public String getNomeEstado() { return "AGENDADA"; }
}
