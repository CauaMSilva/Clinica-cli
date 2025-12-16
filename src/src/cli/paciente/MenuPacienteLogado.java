package cli.paciente;

import cli.consulta.ConsultaInputHelper;
import cli.consulta.MenuConsultaPaciente;
import controllers.ConsultaController;
import controllers.MedicoController;
import controllers.AvaliacaoController;
import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;

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
        ConsultaInputHelper input = new ConsultaInputHelper(scanner, consultaController, avaliacaoController);

        while (true) {
            System.out.println("\n--- PACIENTE LOGADO --- \nNome: " + paciente.getNome() + "  ID: " + paciente.getId());
            System.out.println("1 - Ver médicos disponíveis");
            System.out.println("2 - Agendar consulta");
            System.out.println("3 - Cancelar consulta");
            System.out.println("4 - Avaliar consulta");
            System.out.println("5 - Consultas agendadas");
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
                case 2 -> input.lerNovaConsulta();
                case 3 -> cancelarConsulta(paciente);
                case 4 -> input.menuAvaliacao();
                case 5 -> listarMinhasConsultas(paciente);
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listarMinhasConsultas(Paciente paciente) {
        var consultas = consultaController.listarPorPaciente(paciente.getId());
        if (consultas.isEmpty()) {
            System.out.println("Você não possui consultas registradas.");
            return;
        }

        System.out.println("\n--- SEU HISTÓRICO DE CONSULTAS ---");
        System.out.printf("%-5s | %-12s | %-15s | %-10s\n", "ID", "DATA", "MÉDICO", "STATUS");
        System.out.println("---------------------------------------------------------");
        for (Consulta c : consultas) {
            System.out.printf("%-5d | %-12s | ID Médico: %-5d | %-10s\n",
                    c.getId(), c.getData(), c.getIdMedico(), c.getStatus());
        }
    }

    private void listarMedicos(Paciente paciente) {
        System.out.println("\n--- Médicos disponíveis ---");

        // Lista todos os médicos
        var medicos = paciente.getPlanoSaude().equals("SEM_PLANO")
                ? medicoController.listarMedicos()
                : medicoController.listarPorPlano(paciente.getPlanoSaude());

        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico disponível.");
            return;
        }

        for (var m : medicos) {
            // Calcula média de estrelas do médico
            double media = avaliacaoController.mediaEstrelas(m.getId());

            System.out.println(
                    "ID: " + m.getId() +
                            " | Nome: " + m.getNome() +
                            " | Especialidade: " + m.getEspecialidade() +
                            " | Plano atendido: " + m.getPlanoAtendido() +
                            " | Avaliação: " + String.format("%.1f", media) + " ★"
            );
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
}
