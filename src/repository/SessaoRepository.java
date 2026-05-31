package src.repository;

import src.model.Sessao;
import src.model.estados.*; // Certifique-se de importar seus estados
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SessaoRepository {
    private static final String FILE_PATH = "sessoes.txt";

    public void salvar(Sessao sessao) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linha = sessao.getId() + ";" + 
                           sessao.getIdPaciente() + ";" + 
                           sessao.getIdProfissional() + ";" + 
                           sessao.getData() + ";" + 
                           sessao.getValorBase() + ";" + 
                           sessao.getTipoAtendimento() + ";" + 
                           sessao.getNomeEstado();
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar sessão no arquivo: " + e.getMessage());
        }
    }

    public List<Sessao> listarTodas() {
        List<Sessao> lista = new ArrayList<>();
        File arquivo = new File(FILE_PATH);
        
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                int idPac = Integer.parseInt(dados[1]);
                int idProf = Integer.parseInt(dados[2]);
                String data = dados[3];
                double valor = Double.parseDouble(dados[4]);
                String tipo = dados[5];
                String nomeEstado = dados[6];

                Sessao s = new Sessao(id, idPac, idProf, data, valor, tipo);
                
                // Reconstrói o estado correto
                if (nomeEstado.equals("AGENDADA")) s.setEstado(new EstadoAgendada());
                else if (nomeEstado.equals("REALIZADA")) s.setEstado(new EstadoRealizada());
                else if (nomeEstado.equals("CANCELADA")) s.setEstado(new EstadoCancelada());
                
                lista.add(s);
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler sessões: " + e.getMessage());
        }
        return lista;
    }

    public void salvarTodas(List<Sessao> sessoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Sessao s : sessoes) {
                String linha = s.getId() + ";" + 
                               s.getIdPaciente() + ";" + 
                               s.getIdProfissional() + ";" + 
                               s.getData() + ";" + 
                               s.getValorBase() + ";" + 
                               s.getTipoAtendimento() + ";" + 
                               s.getNomeEstado();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar o arquivo de sessões: " + e.getMessage());
        }
    }

    public Sessao buscarPorId(int id) {
        for (Sessao s : listarTodas()) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}