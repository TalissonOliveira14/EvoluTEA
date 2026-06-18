package src.view;

import src.service.RelatorioService;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import src.model.Paciente;
import src.model.Responsavel;
import src.model.Profissional;
import src.model.Sessao;
import src.model.estados.EstadoCancelada;
import src.repository.PacienteRepository;
import src.repository.ResponsavelRepository;
import src.repository.ProfissionalRepository;
import src.repository.SessaoRepository;
import src.exception.EstadoInvalidoException;
import src.util.CpfValidator;

public class MenuPrincipal {
    private Scanner scanner;
    private DateTimeFormatter formatadorData;
    private PacienteRepository pacienteRepo;
    private ResponsavelRepository responsavelRepo;
    private ProfissionalRepository profesionalRepo;
    private SessaoRepository sessaoRepo;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.pacienteRepo = new PacienteRepository();
        this.responsavelRepo = new ResponsavelRepository();
        this.profesionalRepo = new ProfissionalRepository();
        this.sessaoRepo = new SessaoRepository();
    }

    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibir();
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== EVOLUTEA - MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Responsáveis");
            System.out.println("3. Gerenciar Profissionais");
            System.out.println("4. Gerenciar Sessões e Evoluções (Máquina de Estados)");
            System.out.println("5. Relatórios Gerenciais");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcaoPrincipal(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }

    private void processarOpcaoPrincipal(int opcao) {
        switch (opcao) {
            case 1: submenuPacientes(); break;
            case 2: submenuResponsaveis(); break;
            case 3: submenuProfissionais(); break;
            case 4: submenuSessoes(); break;
            case 5: RelatorioService.exibirPacientesPorNivel(pacienteRepo.listarTodos());
                    RelatorioService.exibirFaturamentoSessoes(sessaoRepo.listarTodas());
                    break;
            case 0: System.out.println("Saindo do sistema... Até logo!"); break;
            default: System.out.println("Opção inválida! Tente novamente.");
        }
    }

   private void formularioEditarPaciente() {
        System.out.println("\n--- MÓDULO: EDITAR PACIENTE ---");
        System.out.print("Digite o ID do paciente que deseja editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Busca o paciente pelo ID
        Paciente alvo = null;
        for (Paciente p : pacienteRepo.listarTodos()) {
            if (p.getId() == id) {
                alvo = p;
                break;
            }
        }

        if (alvo == null) {
            System.out.println("❌ Paciente com ID " + id + " não encontrado.");
            return;
        }

        // Solicitação dos dados para atualização
        System.out.println("Editando paciente: " + alvo.getNome());
        System.out.print("Novo Nome (ou vazio para manter): ");
        String novoNome = scanner.nextLine();
        String nome = novoNome.isEmpty() ? alvo.getNome() : novoNome;

        System.out.print("Novo Telefone (ou vazio para manter): ");
        String novoTelefone = scanner.nextLine();
        String telefone = novoTelefone.isEmpty() ? alvo.getTelefone() : novoTelefone;

        // Atualiza o objeto com os novos dados
        Paciente pacienteAtualizado = new Paciente(
            alvo.getId(), 
            nome, 
            alvo.getCpf(), 
            telefone, 
            alvo.getDataDiagnosticoTEA(), 
            alvo.getHistoricoClinico(), 
            alvo.isTemLaudo(), 
            alvo.getDificuldades(), 
            alvo.getIdResponsavel()
        );

        if (pacienteRepo.atualizar(pacienteAtualizado)) {
            System.out.println("✅ Paciente atualizado com sucesso!");
        } else {
            System.out.println("❌ Erro ao salvar as alterações.");
        }
    }

    private void formularioDeletarPaciente() {
        System.out.println("\n--- MÓDULO: DELETAR PACIENTE ---");
        System.out.print("Digite o ID do paciente para remover: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (pacienteRepo.deletarPorId(id)) {
            System.out.println("🗑️ Paciente removido com sucesso!");
        } else {
            System.out.println("❌ Erro: Paciente não encontrado ou falha na remoção.");
        }
    }

    private void submenuPacientes() {
    int opcao = -1;

    while (opcao != 0) {
        System.out.println("\n--- MÓDULO: GERENCIAR PACIENTES ---");
        System.out.println("1. Cadastrar Novo Paciente");
        System.out.println("2. Listar Todos os Pacientes");
        System.out.println("3. Editar Ficha de Paciente");
        System.out.println("4. Deletar Ficha de Paciente");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    formularioPaciente();
                    break;

                case 2:
                    listarPacientes();
                    break;

                case 3:
                    formularioEditarPaciente();
                    break;

                case 4:
                    formularioDeletarPaciente();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido!");
        }
      }
    }

    private void submenuResponsaveis() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GERENCIAR RESPONSÁVEIS ---");
            System.out.println("1. Cadastrar Novo Responsável");
            System.out.println("2. Listar Todos os Responsáveis");
            System.out.println("3. Editar Ficha de Responsável");
            System.out.println("4. Deletar Ficha de Responsável");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: formularioResponsavel(); break;
                    case 2: listarResponsaveis(); break;
                    case 3: formularioEditarResponsavel(); break;
                    case 4: formularioDeletarResponsavel(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }


    private void submenuProfissionais() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GERENCIAR PROFISSIONAIS ---");
            System.out.println("1. Cadastrar Novo Profissional");
            System.out.println("2. Listar Todos os Profissionais");
            System.out.println("3. Editar Ficha de Profissional");
            System.out.println("4. Deletar Ficha de Profissional");
            System.out.println("5. Ver pacientes direcionados");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: formularioProfissional(); break;
                    case 2: listarProfissionais(); break;
                    case 3: formularioEditarProfissional(); break;
                    case 4: formularioDeletarProfissional(); break;
                    case 5: listarPacientesPorProfissional(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }

    private void listarPacientesPorProfissional() {
    System.out.print("Digite o ID do Profissional: ");
    int id = Integer.parseInt(scanner.nextLine());
    
    // Busca o objeto profissional pelo ID
    Profissional prof = null;
    for (Profissional p : profesionalRepo.listarTodos()) {
        if (p.getId() == id) {
            prof = p;
            break;
        }
    }

    if (prof != null) {
        List<Paciente> pacientes = pacienteRepo.buscarPacientesPorEspecialidade(prof.getEspecialidade());
        System.out.println("\n--- PACIENTES DIRECIONADOS PARA: " + prof.getNome() + " ---");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente com necessidade compatível.");
        } else {
            for (Paciente p : pacientes) {
                System.out.println("-> " + p.getNome() + " (Dificuldade: " + p.getDificuldades() + ")");
            }
        }
    } else {
        System.out.println("❌ Profissional não encontrado!");
    }
}

    private void submenuSessoes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MÓDULO: GESTÃO DE SESSÕES (ESTADO DINÂMICO) ---");
            System.out.println("1. Agendar Nova Sessão");
            System.out.println("2. Listar Todas as Sessões (Repasse Polimórfico)");
            System.out.println("3. Mudar Estado da Sessão (Regras de Transição)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: formularioSessao(); break;
                    case 2: listarSessoes(); break;
                    case 3: formularioAlterarEstadoSessao(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }

    // ==========================================
    // MÉTODOS DE CADASTRO COM TRATAMENTO DE EXCEÇÃO CUSTOMIZADO
    // ==========================================

private void formularioPaciente() {
        System.out.println("\n--- CADASTRO DE PACIENTE ---");

        // 1. Definição do Responsável (Mantido)
        System.out.print("O paciente possui um representante legal cadastrado? (S/N): ");
        String possuiResponsavel = scanner.nextLine();
        
        int idEscolhido = 0; 
        if (possuiResponsavel.equalsIgnoreCase("S")) {
            List<Responsavel> responsaveis = responsavelRepo.listarTodos();
            if (responsaveis.isEmpty()) {
                System.out.println("❌ Erro: Não há responsáveis cadastrados.");
                return;
            }
            System.out.print("Digite o ID do Responsável escolhido: ");
            idEscolhido = Integer.parseInt(scanner.nextLine());
        }

        // 2. Coleta dos dados básicos (Mantido)
        System.out.print("ID do Paciente: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            if (!CpfValidator.isCpfValido(cpf)) {
                System.out.println("❌ ERRO: CPF inválido. Por favor, digite novamente.");
            }
        } while (!CpfValidator.isCpfValido(cpf));

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        LocalDate dataDiag = null;
        while (dataDiag == null) {
            try {
                System.out.print("Data do Diagnóstico (dd/mm/aaaa): ");
                String dataTexto = scanner.nextLine();
                dataDiag = LocalDate.parse(dataTexto, formatadorData);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("❌ Data inválida! Digite uma data válida no formato dd/MM/aaaa.");
            }
        }

        System.out.print("Histórico Clínico: ");
        String historico = scanner.nextLine();

        // 3. NOVA LÓGICA DE TRIAGEM (Aplicada)
        System.out.print("O paciente possui laudo médico para TEA? (S/N): ");
        boolean temLaudo = scanner.nextLine().equalsIgnoreCase("S");

        System.out.println("--- SELECIONE AS DIFICULDADES (Digite 0 para finalizar) ---");
        System.out.println("1. Fala\n2. Motor\n3. Comportamento\n4. Socialização");

        List<String> listaDificuldades = new ArrayList<>();
        int opcaoDificuldade = -1;
        while (opcaoDificuldade != 0) {
            System.out.print("Escolha uma opção (ou 0 para salvar): ");
            try {
                opcaoDificuldade = Integer.parseInt(scanner.nextLine());
                switch (opcaoDificuldade) {
                    case 1: listaDificuldades.add("Fala"); break;
                    case 2: listaDificuldades.add("Motor"); break;
                    case 3: listaDificuldades.add("Comportamento"); break;
                    case 4: listaDificuldades.add("Socialização"); break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }

        // 4. Instanciação com o novo construtor
        Paciente novoPaciente = new Paciente(
            id,
            nome,
            cpf,
            telefone,
            dataDiag,
            historico,
            temLaudo,           // Campo novo
            listaDificuldades,  // Campo novo
            idEscolhido
        );

        // 5. Salvar (Agora com a regra atualizada)
        pacienteRepo.salvar(novoPaciente);
        System.out.println("🎉 Paciente cadastrado com sucesso!");

        System.out.println("\n--- ENCAMINHAMENTO SUGERIDO ---");
        for (String diff : listaDificuldades) {
            List<Profissional> sugestoes = profesionalRepo.buscarPorDificuldade(diff);
            if (!sugestoes.isEmpty()) {
                System.out.println("Para a dificuldade [" + diff + "], sugerimos: ");
                for (Profissional prof : sugestoes) {
                    System.out.println(" -> " + prof.getNome() + " (" + prof.getEspecialidade() + ")");
                }
            } else {
                System.out.println("Nenhum especialista cadastrado para: [" + diff + "]");
            }
        }
    }
    private void formularioResponsavel() {
        System.out.println("\n--- CADASTRO DE RESPONSÁVEL ---");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            if (!CpfValidator.isCpfValido(cpf)) {
                System.out.println("❌ ERRO: CPF inválido. Por favor, digite novamente.");
            }
        } while (!CpfValidator.isCpfValido(cpf));

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Grau de Parentesco: ");
        String parentesco = scanner.nextLine();

        Responsavel novoResp = new Responsavel(id, nome, cpf, telefone, email, parentesco);
        responsavelRepo.salvar(novoResp);
        System.out.println("🎉 Responsável cadastrado com sucesso!");
    }

    private void formularioProfissional() {
    System.out.println("\n--- CADASTRO DE PROFISSIONAL ---");
    System.out.print("ID: ");
    int id = Integer.parseInt(scanner.nextLine());
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    
    String cpf;
    do {
        System.out.print("CPF: ");
        cpf = scanner.nextLine();
        if (!CpfValidator.isCpfValido(cpf)) {
            System.out.println("❌ ERRO: CPF inválido. Por favor, digite novamente.");
        }
    } while (!CpfValidator.isCpfValido(cpf));

    System.out.print("Telefone: ");
    String telefone = scanner.nextLine();
    System.out.print("Registro Profissional (CRP/CRM): ");
    String registro = scanner.nextLine();

    // --- BLOCO NOVO E CORRIGIDO ---
    System.out.println("Selecione a Especialidade:");
    System.out.println("1. Fonoaudiólogo");
    System.out.println("2. Psicólogo");
    System.out.println("3. Terapeuta Ocupacional");
    System.out.println("4. Pedagogo");
    System.out.print("Opção: ");
    int op = Integer.parseInt(scanner.nextLine());
    
    String especialidade;
    switch (op) {
        case 1: especialidade = "Fonoaudiólogo"; break;
        case 2: especialidade = "Psicólogo"; break;
        case 3: especialidade = "Terapeuta Ocupacional"; break;
        case 4: especialidade = "Pedagogo"; break;
        default: especialidade = "Outros";
    }
    // ------------------------------

    Profissional novoProf = new Profissional(id, nome, cpf, telefone, registro, especialidade);
    profesionalRepo.salvar(novoProf);
    System.out.println("🎉 Profissional cadastrado com sucesso!");
}
    private void formularioSessao() {
        try {
            System.out.println("\n--- AGENDAMENTO DE SESSÃO ---");
            System.out.print("ID da Sessão: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Paciente: ");
            int idPac = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Profissional: ");
            int idProf = Integer.parseInt(scanner.nextLine());
            System.out.print("Data da Sessão (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            System.out.print("Valor Base da Consulta: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Tipo de Atendimento (PARTICULAR ou PLANO): ");
            String tipo = scanner.nextLine();

            Sessao novaSessao = new Sessao(id, idPac, idProf, data, valor, tipo);
            sessaoRepo.salvar(novaSessao);
            System.out.println("🎉 Sessão agendada! Estado inicial: [AGENDADA]");
        } catch (Exception e) {
            System.out.println("❌ Erro ao processar o agendamento.");
        }
    }

    // ==========================================
    // MÉTODOS DE EXIBIÇÃO / LISTAGEM
    // ==========================================

    private void listarPacientes() {
        System.out.println("\n=== LISTA DE PACIENTES CADASTRADOS ===");
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()) { System.out.println("Nenhum paciente encontrado."); return; }
        for (Paciente p : pacientes) {
           String status = p.isTemLaudo() ? "Com Laudo" : "Investigação";
           System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | Status: " + status + " | Dificuldades: " + p.getDificuldades());
        }
    }

    private void listarResponsaveis() {
        System.out.println("\n=== LISTA DE RESPONSÁVEIS CADASTRADOS ===");
        List<Responsavel> responsaveis = responsavelRepo.listarTodos();
        if (responsaveis.isEmpty()) { System.out.println("Nenhum responsável encontrado."); return; }
        for (Responsavel r : responsaveis) {
            System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome() + " | Grau: " + r.getGrauParentesco());
        }
    }

    private void listarProfissionais() {
        System.out.println("\n=== LISTA DE PROFISSIONAIS CADASTRADOS ===");
        List<Profissional> profissionais = profesionalRepo.listarTodos();
        if (profissionais.isEmpty()) { System.out.println("Nenhum profissional encontrado."); return; }
        for (Profissional p : profissionais) {
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | Especialidade: " + p.getEspecialidade());
        }
    }

    private void listarSessoes() {
        System.out.println("\n=== HISTÓRICO DE SESSÕES (APLICAÇÃO DE POLIMORFISMO) ===");
        List<Sessao> sessoes = sessaoRepo.listarTodas();
        if (sessoes.isEmpty()) { System.out.println("Nenhuma sessão registrada no histórico."); return; }
        for (Sessao s : sessoes) {
            System.out.println("ID: " + s.getId() + " | Paciente ID: " + s.getIdPaciente() + " | Data: " + s.getData() + " | Tipo: " + s.getTipoAtendimento());
            System.out.println("🔄 Estado Atual: [" + s.getNomeEstado() + "]");
            System.out.println("💰 Valor Base: R$ " + s.getValorBase() + " -> Repasse Líquido (Polimórfico): R$ " + s.getValorLiquidoProfissional());
            System.out.println("----------------------------------------------------------------");
        }
    }

    // ==========================================
    // TRANSIÇÃO DE ESTADOS DA MÁQUINA DE ESTADOS
    // ==========================================

    private void formularioAlterarEstadoSessao() {
    System.out.println("\n--- ALTERAÇÃO DE ESTADO DA SESSÃO ---");
    List<Sessao> sessoes = sessaoRepo.listarTodas();
    if (sessoes.isEmpty()) { 
        System.out.println("Nenhuma sessão no sistema."); 
        return; 
    }

    System.out.print("Digite o ID da sessão que deseja alterar: ");
    int id = Integer.parseInt(scanner.nextLine());

    Sessao alvo = null;
    for (Sessao s : sessoes) { 
        if (s.getId() == id) { 
            alvo = s; 
            break; 
        } 
    }
    
    if (alvo == null) { 
        System.out.println("Sessão não encontrada!"); 
        return; 
    }

    System.out.println("Estado atual da sessão: [" + alvo.getNomeEstado() + "]");
    System.out.println("O que deseja fazer?");
    System.out.println("1. Avançar (Realizar Sessão)");
    System.out.println("2. Cancelar Sessão");
    System.out.print("Escolha: ");
    String opcao = scanner.nextLine();

    try {
        if (opcao.equals("1")) {
            alvo.avancarFluxo(); // Usa a regra do Padrão State
        } else if (opcao.equals("2")) {
            alvo.setEstado(new EstadoCancelada()); // Troca forçada para Cancelada
        } else {
            System.out.println("Opção inválida!");
            return;
        }
        
        sessaoRepo.salvarTodas(sessoes);
        System.out.println("🎉 Sucesso! Novo estado: [" + alvo.getNomeEstado() + "]");
    } catch (EstadoInvalidoException e) {
        System.out.println("\n❌ REGRA BARRADA (Exceção Capturada):");
        System.out.println(e.getMessage());
    }
}

    // ==========================================
    // MÉTODOS DE EDIÇÃO E EXCLUSÃO DE RESPONSÁVEL
    // ==========================================

    private void formularioEditarResponsavel() {
        System.out.println("\n--- EDIÇÃO DE FICHA DE RESPONSÁVEL ---");
        System.out.print("Digite o ID do responsável que deseja alterar: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Responsavel> responsaveis = responsavelRepo.listarTodos();
        Responsavel alvo = null;
        for (Responsavel r : responsaveis) { if (r.getId() == id) { alvo = r; break; } }
        if (alvo == null) { System.out.println("⚠️ Responsável não encontrado."); return; }

        System.out.print("Nome atual [" + alvo.getNome() + "]: ");
        String nome = scanner.nextLine(); if(nome.isEmpty()) nome = alvo.getNome();

        System.out.print("CPF atual [" + alvo.getCpf() + "]: ");
        String cpf = scanner.nextLine(); if(cpf.isEmpty()) cpf = alvo.getCpf();

        System.out.print("Telefone atual [" + alvo.getTelefone() + "]: ");
        String telefone = scanner.nextLine(); if(telefone.isEmpty()) telefone = alvo.getTelefone();

        System.out.print("E-mail atual [" + alvo.getEmail() + "]: ");
        String email = scanner.nextLine(); if(email.isEmpty()) email = alvo.getEmail();

        System.out.print("Grau de Parentesco atual [" + alvo.getGrauParentesco() + "]: ");
        String parentesco = scanner.nextLine(); if(parentesco.isEmpty()) parentesco = alvo.getGrauParentesco();

        Responsavel updated = new Responsavel(id, nome, cpf, telefone, email, parentesco);
        if (responsavelRepo.atualizar(updated)) System.out.println("✅ Ficha do responsável alterada com sucesso!");
    }

    private void formularioDeletarResponsavel() {
        System.out.print("\nDigite o ID do responsável para remover: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        // Impede a deleção se houver vínculos com pacientes (Item 1.c - Regra de Negócio)
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        for (Paciente p : pacientes) {
            if (p.getIdResponsavel() == id) {
                System.out.println("❌ Erro: Não é possível deletar este responsável pois ele possui o paciente '" + p.getNome() + "' vinculado!");
                return;
            }
        }
        
        if (responsavelRepo.deletarPorId(id)) System.out.println("🗑️ Responsável excluído do arquivo!");
        else System.out.println("⚠️ ID não encontrado.");
    }

    // ==========================================
    // MÉTODOS DE EDIÇÃO E EXCLUSÃO DE PROFISSIONAL
    // ==========================================

    private void formularioEditarProfissional() {
        System.out.println("\n--- EDIÇÃO DE FICHA DE PROFISSIONAL ---");
        System.out.print("Digite o ID do profissional que deseja alterar: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Profissional> profissionais = profesionalRepo.listarTodos();
        Profissional alvo = null;
        for (Profissional p : profissionais) { if (p.getId() == id) { alvo = p; break; } }
        if (alvo == null) { System.out.println("⚠️ Profissional não encontrado."); return; }

        System.out.print("Nome atual [" + alvo.getNome() + "]: ");
        String nome = scanner.nextLine(); if(nome.isEmpty()) nome = alvo.getNome();

        System.out.print("CPF atual [" + alvo.getCpf() + "]: ");
        String cpf = scanner.nextLine(); if(cpf.isEmpty()) cpf = alvo.getCpf();

        System.out.print("Telefone atual [" + alvo.getTelefone() + "]: ");
        String telefone = scanner.nextLine(); if(telefone.isEmpty()) telefone = alvo.getTelefone();

        System.out.print("Registro Profissional atual [" + alvo.getRegistroProfissional() + "]: ");
        String registro = scanner.nextLine(); if(registro.isEmpty()) registro = alvo.getRegistroProfissional();

        System.out.print("Especialidade atual [" + alvo.getEspecialidade() + "]: ");
        String esp = scanner.nextLine(); if(esp.isEmpty()) esp = alvo.getEspecialidade();

        Profissional updated = new Profissional(id, nome, cpf, telefone, registro, esp);
        if (profesionalRepo.atualizar(updated)) System.out.println("✅ Cadastro do profissional atualizado!");
    }

    private void formularioDeletarProfissional() {
        System.out.print("\nDigite o ID do profissional para remover: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (profesionalRepo.deletarPorId(id)) System.out.println("🗑️ Profissional removido do arquivo!");
        else System.out.println("⚠️ ID não encontrado.");
    }
}