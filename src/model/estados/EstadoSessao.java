package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException;

public interface EstadoSessao {
    void proximo(Sessao sessao) throws EstadoInvalidoException;
    
    String getNomeEstado();
}