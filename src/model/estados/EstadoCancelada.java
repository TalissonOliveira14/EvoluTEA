package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException;

/**
 * Representa o estado final de uma sessão cancelada.
 * Impede qualquer tentativa de avançar o fluxo.
 */
public class EstadoCancelada implements EstadoSessao {

    @Override
    public void proximo(Sessao sessao) throws EstadoInvalidoException {
        // Regra de negócio: sessão cancelada é um estado terminal de bloqueio
        throw new EstadoInvalidoException("Erro: Esta sessão foi CANCELADA e não permite novas alterações ou avanços.");
    }

    @Override
    public String getNomeEstado() {
        return "CANCELADA";
    }
}