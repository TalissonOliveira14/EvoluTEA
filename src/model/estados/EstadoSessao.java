package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException;

public interface EstadoSessao {
    /**
     * Define o próximo estado da sessão.
     * @param sessao A sessão que está mudando de estado.
     * @throws EstadoInvalidoException Caso a regra de negócio impeça a transição.
     */
    void proximo(Sessao sessao) throws EstadoInvalidoException;
    
    String getNomeEstado();
}