package src.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import src.model.Profissional;

public class ProfissionalRepository {
    private static final String FILE_PATH = "profissionais.txt";

    public void salvar(Profissional profesional) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linha = profesional.getId() + ";" +
                           profesional.getNome() + ";" +
                           profesional.getCpf() + ";" +
                           profesional.getTelefone() + ";" +
                           profesional.getRegistroProfissional() + ";" +
                           profesional.getEspecialidade();
            bw.write(linha);
            bw.newLine();
            System.out.println("💾 Dados salvos com sucesso em '" + FILE_PATH + "'!");
        } catch (IOException e) {
            System.out.println("❌ Erro grave ao salvar o profissional: " + e.getMessage());
        }
    }

    public List<Profissional> listarTodos() {
        List<Profissional> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String cpf = dados[2];
                String telefone = dados[3];
                String registro = dados[4];
                String especialidade = dados[5];
                
                Profissional p = new Profissional(id, nome, cpf, telefone, registro, especialidade);
                lista.add(p);
            }
        } catch (IOException e) {
            // Retorna lista vazia se não houver arquivo
        }
        return lista;
    }

    public List<Profissional> buscarPorDificuldade(String dificuldadePaciente) {
        List<Profissional> encontrados = new ArrayList<>();
        // Correção: Carregamos a lista toda primeiro
        List<Profissional> todosOsProfissionais = listarTodos(); 
        
        // Agora iteramos sobre a lista que acabamos de carregar
        for (Profissional p : todosOsProfissionais) {
            if (src.util.RegrasClinicas.getDificuldadePelaEspecialidade(p.getEspecialidade())
                .equalsIgnoreCase(dificuldadePaciente)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    // ✨ NOVO MÉTODO: Atualiza os dados de um profissional existente
    public boolean atualizar(Profissional profissionalAtualizado) {
        List<Profissional> profissionais = listarTodos();
        boolean modificado = false;

        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getId() == profissionalAtualizado.getId()) {
                profissionais.set(i, profissionalAtualizado);
                modificado = true;
                break;
            }
        }

        if (modificado) {
            salvarTodos(profissionais);
        }
        return modificado;
    }

    public boolean deletarPorId(int id) {
        List<Profissional> profissionais = listarTodos();
        boolean removido = false;

        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getId() == id) {
                profissionais.remove(i);
                removido = true;
                break;
            }
        }

        if (removido) {
            salvarTodos(profissionais);
        }
        return removido;
    }

    private void salvarTodos(List<Profissional> profissionais) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Profissional p : profissionais) {
                String linha = p.getId() + ";" +
                               p.getNome() + ";" +
                               p.getCpf() + ";" +
                               p.getTelefone() + ";" +
                               p.getRegistroProfissional() + ";" +
                               p.getEspecialidade();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar o arquivo de profissionais: " + e.getMessage());
        }
    }
}