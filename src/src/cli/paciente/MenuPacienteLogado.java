package cli.paciente;

import controllers.ConsultaController;
import controllers.MedicoController;
import controllers.AvaliacaoController;
import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;

import java.util.List;
import java.util.Scanner;

public class MenuPacienteLogado {

    private final Scanner scanner = new Scanner(System.in);

    private final AvaliacaoController avaliacaoController;
    private final MedicoController medicoController;
    private final ConsultaController consultaController;

    public MenuPacienteLogado(
            MedicoController medicoController,
            ConsultaController consultaController,
            AvaliacaoController avaliacaoController
    ) {
        this.medicoController = medicoController;
        this.consultaController = consultaController;
        this.avaliacaoController = avaliacaoController;
    }

    public void abrir(Paciente paciente) {
        while (true) {
            System.out.println("\n--- PACIENTE LOGADO ---\n" +
                    " id: " + paciente.getId() +
                    " | nome: " + paciente.getNome() + "\n");
            System.out.println("1 - Ver médicos disponíveis");
            System.out.println("2 - Agendar consulta");
            System.out.println("3 - Cancelar consulta");
            System.out.println("4 - Avaliar consulta");
            System.out.println("5 - Listar minhas consultas");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");

            int op;
            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (op) {
                case 1 -> listarMedicos(paciente);
                case 2 -> agendarConsulta(paciente);
                case 3 -> cancelarConsulta(paciente);
                case 4 -> avaliarConsulta(paciente);
                case 5 -> listarMinhasConsultas(paciente);
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listarMinhasConsultas(Paciente paciente) {
        List<Consulta> consultas = consultaController.listarPorPaciente(paciente.getId());
        for(Consulta c: consultas){
            System.out.println("Id da consulta: " + c.getId() +
                    " id do médico: " + c.getIdMedico() +
                    " data da consulta: " + c.getData());
        }
    }
    private void listarMedicos(Paciente paciente) {

        // Lista todos os médicos
        var medicos = paciente.getPlanoSaude().equals("SEM_PLANO")
                ? medicoController.listarMedicos()
                : medicoController.listarPorPlano(paciente.getPlanoSaude());

        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico disponível.");
            return;
        }

        for (var m : medicos) {
            double media = avaliacaoController.mediaEstrelas(m.getId());
            System.out.println(
                    "ID: " + m.getId() +
                            " | Nome: " + m.getNome() +
                            " | Especialidade: " + m.getEspecialidade() +
                            " | Plano atendido: " + m.getPlanoAtendido() +
                            " | Avaliação: " + String.format("%.1f", media) + " ★");
        }
    }


    private void agendarConsulta(Paciente paciente) {
        System.out.print("ID do médico: ");
        int idMedico;
        try {
            idMedico = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Medico medico = medicoController.buscarPorId(idMedico);
        if (medico == null) {
            System.out.println("Médico não existe.");
            return;
        }

        System.out.print("Data da consulta (dd/MM/yyyy): ");
        String data = scanner.nextLine();

        try {
            consultaController.agendar(paciente.getId(), idMedico, data);
            System.out.println("Consulta agendada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao agendar: " + e.getMessage());
        }
    }

    private void cancelarConsulta(Paciente paciente) {
        System.out.print("ID da consulta a cancelar: ");
        int idConsulta;
        try {
            idConsulta = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        try {
            consultaController.cancelar(idConsulta);
            System.out.println("Consulta cancelada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar: " + e.getMessage());
        }
    }

    private void avaliarConsulta(Paciente paciente) {
        System.out.print("ID da consulta que deseja avaliar: ");
        int idConsulta;
        try {
            idConsulta = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
        Consulta consulta = consultaController.buscarPorId(idConsulta);

        if (consulta.getStatus().equals("CANCELADA")){
            System.out.println("Consulta cancelada, não pode ser avaliada");
            return;
        }
        if (consulta == null || consulta.getIdPaciente() != paciente.getId()) {
            System.out.println("Consulta inválida ou não pertence a você.");
            return;
        }

        int idMedico = consulta.getIdMedico();

        System.out.print("Avaliação (1 a 5 estrelas): ");
        int estrelas;
        try {
            estrelas = Integer.parseInt(scanner.nextLine());
            if (estrelas < 1 || estrelas > 5) {
                System.out.println("Estrelas inválidas. Deve ser entre 1 e 5.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Número de estrelas inválido.");
            return;
        }

        System.out.print("Comentário: ");
        String comentario = scanner.nextLine();

        avaliacaoController.avaliar(avaliacaoController.gerarNovoId(), idConsulta, idMedico, estrelas, comentario);
        System.out.println("Avaliação registrada com sucesso!");
    }
}
