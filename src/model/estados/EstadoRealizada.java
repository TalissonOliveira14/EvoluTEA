package src.model.estados;

import src.model.Sessao;
import src.exception.EstadoInvalidoException; // Certifique-se de importar sua exceção

public class EstadoRealizada implements EstadoSessao {
    @Override
    public void proximo(Sessao sessao) throws EstadoInvalidoException {
        // Em vez de printar, lançamos a exceção. 
        // O MenuPrincipal vai capturar isso com um try-catch.
        throw new EstadoInvalidoException("Erro: A sessão já está REALIZADA e não pode ser alterada.");
    }

    @Override
    public String getNomeEstado() { return "REALIZADA"; }
}