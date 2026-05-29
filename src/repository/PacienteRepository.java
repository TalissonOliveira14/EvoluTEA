package src.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.model.Paciente;

public class PacienteRepository {
    private static final String FILE_PATH = "pacientes.txt";
    private DateTimeFormatter formatadorData;

    public PacienteRepository() {
        this.formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public void salvar(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linha = paciente.getId() + ";" +
                           paciente.getNome() + ";" +
                           paciente.getCpf() + ";" +
                           paciente.getTelefone() + ";" +
                           paciente.getDataDiagnosticoTEA().format(formatadorData) + ";" +
                           paciente.getHistoricoClinico() + ";" +
                           paciente.getNecessidadeSuporte() + ";" +
                           paciente.getIdResponsavel();
            bw.write(linha);
            bw.newLine();
            System.out.println("💾 Dados salvos com sucesso em '" + FILE_PATH + "'!");
        } catch (IOException e) {
            System.out.println("❌ Erro grave ao tentar salvar no arquivo: " + e.getMessage());
        }
    }

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String cpf = dados[2];
                String telefone = dados[3];
                LocalDate dataDiag = LocalDate.parse(dados[4], formatadorData);
                String historico = dados[5];
                String suporte = dados[6];
                int idResp = Integer.parseInt(dados[7]);
                
                Paciente p = new Paciente(id, nome, cpf, telefone, dataDiag, historico, suporte, idResp);
                lista.add(p);
            }
        } catch (IOException e) {
            // Retorna lista vazia se não houver arquivo
        }
        return lista;
    }

    // ✨ NOVO MÉTODO: Atualiza os dados de um paciente existente
    public boolean atualizar(Paciente pacienteAtualizado) {
        List<Paciente> pacientes = listarTodos();
        boolean modificado = false;

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == pacienteAtualizado.getId()) {
                pacientes.set(i, pacienteAtualizado); // Substitui o antigo pelo atualizado
                modificado = true;
                break;
            }
        }

        if (modificado) {
            salvarTodos(pacientes);
        }
        return modificado;
    }

    public boolean deletarPorId(int id) {
        List<Paciente> pacientes = listarTodos();
        boolean removido = false;
        int idResponsavelDoDeletado = -1;

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == id) {
                idResponsavelDoDeletado = pacientes.get(i).getIdResponsavel();
                pacientes.remove(i);
                removido = true;
                break;
            }
        }

        if (removido) {
            salvarTodos(pacientes);

            boolean responsavelAindaTemFilhos = false;
            for (Paciente p : pacientes) {
                if (p.getIdResponsavel() == idResponsavelDoDeletado) {
                    responsavelAindaTemFilhos = true;
                    break;
                }
            }

            if (!responsavelAindaTemFilhos && idResponsavelDoDeletado != -1) {
                ResponsavelRepository respRepo = new ResponsavelRepository();
                boolean respDeletado = respRepo.deletarPorId(idResponsavelDoDeletado);
                if (respDeletado) {
                    System.out.println("🛡️ Cascata Ativa: O responsável (ID: " + idResponsavelDoDeletado + ") não possuía outros vínculos e foi removido para evitar dados órfãos!");
                }
            } else {
                System.out.println("ℹ️ Cascata Segura: O responsável foi mantido porque ainda possui outros dependentes cadastrados.");
            }
        }
        return removido;
    }

    // Método auxiliar interno para reescrever o arquivo limpando o antigo
    private void salvarTodos(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Paciente paciente : pacientes) {
                String linha = paciente.getId() + ";" +
                               paciente.getNome() + ";" +
                               paciente.getCpf() + ";" +
                               paciente.getTelefone() + ";" +
                               paciente.getDataDiagnosticoTEA().format(formatadorData) + ";" +
                               paciente.getHistoricoClinico() + ";" +
                               paciente.getNecessidadeSuporte() + ";" +
                               paciente.getIdResponsavel();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar o arquivo de pacientes: " + e.getMessage());
        }
    }
}