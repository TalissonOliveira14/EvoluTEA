package src.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.model.Sessao;

public class SessaoRepository {
    private final String FILE_PATH = "sessoes.txt";

    public SessaoRepository() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException ignored) {}
        }
    }

    public void salvar(Sessao sessao) {
        List<Sessao> sessoes = listarTodas();
        sessoes.add(sessao);
        salvarTodas(sessoes);
    }

    public List<Sessao> listarTodas() {
        List<Sessao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    Sessao s = new Sessao(
                        Integer.parseInt(dados[0]),
                        Integer.parseInt(dados[1]),
                        Integer.parseInt(dados[2]),
                        dados[3],
                        Double.parseDouble(dados[4]),
                        dados[5]
                    );
                    s.setEstado(dados[6]);
                    lista.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de sessões.");
        }
        return lista;
    }

    public void salvarTodas(List<Sessao> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Sessao s : lista) {
                bw.write(s.getId() + ";" + s.getIdPaciente() + ";" + s.getIdProfissional() + ";" +
                         s.getData() + ";" + s.getValorBase() + ";" + s.getTipoAtendimento() + ";" + s.getEstado());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo de sessões.");
        }
    }
}