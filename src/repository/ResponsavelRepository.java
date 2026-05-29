package src.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import src.model.Responsavel;

public class ResponsavelRepository {
    private static final String FILE_PATH = "responsaveis.txt";

    public void salvar(Responsavel responsavel) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linha = responsavel.getId() + ";" +
                           responsavel.getNome() + ";" +
                           responsavel.getCpf() + ";" +
                           responsavel.getTelefone() + ";" +
                           responsavel.getEmail() + ";" +
                           responsavel.getGrauParentesco();
            bw.write(linha);
            bw.newLine();
            System.out.println("💾 Dados salvos com sucesso em '" + FILE_PATH + "'!");
        } catch (IOException e) {
            System.out.println("❌ Erro grave ao salvar o responsável: " + e.getMessage());
        }
    }

    public List<Responsavel> listarTodos() {
        List<Responsavel> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String cpf = dados[2];
                String telefone = dados[3];
                String email = dados[4];
                String parentesco = dados[5];
                
                Responsavel r = new Responsavel(id, nome, cpf, telefone, email, parentesco);
                lista.add(r);
            }
        } catch (IOException e) {
            // Retorna lista vazia se não houver arquivo
        }
        return lista;
    }

    // ✨ NOVO MÉTODO: Atualiza os dados de um responsável existente
    public boolean atualizar(Responsavel responsavelAtualizado) {
        List<Responsavel> responsaveis = listarTodos();
        boolean modificado = false;

        for (int i = 0; i < responsaveis.size(); i++) {
            if (responsaveis.get(i).getId() == responsavelAtualizado.getId()) {
                responsaveis.set(i, responsavelAtualizado);
                modificado = true;
                break;
            }
        }

        if (modificado) {
            salvarTodos(responsaveis);
        }
        return modificado;
    }

    public boolean deletarPorId(int id) {
        List<Responsavel> responsaveis = listarTodos();
        boolean removido = false;

        for (int i = 0; i < responsaveis.size(); i++) {
            if (responsaveis.get(i).getId() == id) {
                responsaveis.remove(i);
                removido = true;
                break;
            }
        }

        if (removido) {
            salvarTodos(responsaveis);
        }
        return removido;
    }

    private void salvarTodos(List<Responsavel> responsaveis) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Responsavel r : responsaveis) {
                String linha = r.getId() + ";" +
                               r.getNome() + ";" +
                               r.getCpf() + ";" +
                               r.getTelefone() + ";" +
                               r.getEmail() + ";" +
                               r.getGrauParentesco();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar o arquivo de responsáveis: " + e.getMessage());
        }
    }
}