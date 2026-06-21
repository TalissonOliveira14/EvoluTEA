package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException; // Certifique-se de importar sua exceção

public class EstadoRealizada implements EstadoSessao {
    @Override
    public void proximo(Sessao sessao) throws EstadoInvalidoException {
        
        throw new EstadoInvalidoException("Erro: A sessão já está REALIZADA e não pode ser alterada.");
    }

    @Override
    public String getNomeEstado() { return "REALIZADA"; }
}