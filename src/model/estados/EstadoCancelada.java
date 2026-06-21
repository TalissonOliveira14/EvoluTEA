package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException;


public class EstadoCancelada implements EstadoSessao {

    @Override
    public void proximo(Sessao sessao) throws EstadoInvalidoException {
        
        throw new EstadoInvalidoException("Erro: Esta sessão foi CANCELADA e não permite novas alterações ou avanços.");
    }

    @Override
    public String getNomeEstado() {
        return "CANCELADA";
    }
}