package src.repository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import src.model.Paciente;

public class PacienteRepository {
    private static final String FILE_PATH = "pacientes.txt";
    private DateTimeFormatter formatadorData;

    public PacienteRepository() {
        this.formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    private String converterDificuldadesParaString(List<String> dificuldades) {
        return String.join(",", dificuldades);
    }

    public void salvar(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linha = paciente.getId() + ";" +
                           paciente.getNome() + ";" +
                           paciente.getCpf() + ";" +
                           paciente.getTelefone() + ";" +
                           paciente.getDataDiagnosticoTEA().format(formatadorData) + ";" +
                           paciente.getHistoricoClinico() + ";" +
                           paciente.isTemLaudo() + ";" +
                           converterDificuldadesParaString(paciente.getDificuldades()) + ";" +
                           paciente.getIdResponsavel();
            bw.write(linha);
            bw.newLine();
            System.out.println("💾 Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        File arquivo = new File(FILE_PATH);
        if (!arquivo.exists()) return lista;

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
                boolean temLaudo = Boolean.parseBoolean(dados[6]);
                List<String> dificuldades = Arrays.asList(dados[7].split(","));
                int idResp = Integer.parseInt(dados[8]);
                
                lista.add(new Paciente(id, nome, cpf, telefone, dataDiag, historico, temLaudo, dificuldades, idResp));
            }
        } catch (IOException e) { /* Ignora */ }
        return lista;
    }

    public List<Paciente> buscarPacientesPorEspecialidade(String especialidadeProfissional) {
    
    String dificuldade = src.util.RegrasClinicas.getDificuldadePelaEspecialidade(especialidadeProfissional);
    
    List<Paciente> listaFiltrada = new ArrayList<>();
    
    for (Paciente p : listarTodos()) {
        if (p.getDificuldades().contains(dificuldade)) {
            listaFiltrada.add(p);
        }
    }
    return listaFiltrada;
}

    private void salvarTodos(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Paciente paciente : pacientes) {
                String linha = paciente.getId() + ";" +
                               paciente.getNome() + ";" +
                               paciente.getCpf() + ";" +
                               paciente.getTelefone() + ";" +
                               paciente.getDataDiagnosticoTEA().format(formatadorData) + ";" +
                               paciente.getHistoricoClinico() + ";" +
                               paciente.isTemLaudo() + ";" +
                               converterDificuldadesParaString(paciente.getDificuldades()) + ";" +
                               paciente.getIdResponsavel();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }

    public boolean atualizar(Paciente pacienteAtualizado) {
        List<Paciente> pacientes = listarTodos();
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == pacienteAtualizado.getId()) {
                pacientes.set(i, pacienteAtualizado);
                salvarTodos(pacientes);
                return true;
            }
        }
        return false;
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
                    System.out.println("🛡️ Regra de Negócio: Responsável (ID: " + idResponsavelDoDeletado + ") removido por não ter mais dependentes.");
                }
            }
        }
        return removido;
    }
}