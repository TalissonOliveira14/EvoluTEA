package src.repository;

import src.model.Sessao;
import java.util.ArrayList;
import java.util.List;

public class SessaoRepository {
    private List<Sessao> sessoes = new ArrayList<>();

    public void salvar(Sessao sessao) {
        sessoes.add(sessao);
    }

    public List<Sessao> listarTodas() {
        return this.sessoes;
    }

    public void salvarTodas(List<Sessao> lista) {
        this.sessoes = lista;
    }

    public Sessao buscarPorId(int id) {
        for (Sessao s : sessoes) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}