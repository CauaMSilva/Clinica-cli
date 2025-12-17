package cli.medico;

import controllers.ConsultaController;
import controllers.MedicoController;
import models.entities.Consulta;
import models.entities.Medico;

import java.util.List;
import java.util.Scanner;

public class MenuMedicoLogado {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsultaController consultaController;
    private final MedicoController medicoController;

    public MenuMedicoLogado(
            MedicoController medicoController,
            ConsultaController consultaController
    ) {
        this.consultaController = consultaController;
        this.medicoController = medicoController;
    }

    public void abrir(Medico medico) {
        while (true) {
            System.out.println("\n--- MÉDICO LOGADO ---");
            System.out.println("Dr(a). " + medico.getNome());
            System.out.println("Especialidade: " + medico.getEspecialidade());

            System.out.println("\n1 - Ver consultas pendentes");
            System.out.println("2 - Realizar consulta");
            System.out.println("3 - Atualizar meus dados");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> verConsultasPendentes(medico);
                case 2 -> realizarConsulta(medico);
                case 3 -> atualizarDados(medico);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    private void verConsultasPendentes(Medico medico) {
        List<Consulta> consultas =
                consultaController.listarPorMedico(medico.getId());

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada.");
            return;
        }

        consultas.stream()
                .filter(c -> c.getStatus().equalsIgnoreCase("AGENDADA"))
                .forEach(c -> System.out.println(
                        "ID: " + c.getId() +
                                " | Paciente: " + c.getIdPaciente() +
                                " | Data: " + c.getData()
                ));
    }
    private void realizarConsulta(Medico medico) {
        System.out.print("ID da consulta: ");
        int idConsulta = Integer.parseInt(scanner.nextLine());

        System.out.println("Descrição da consulta:");
        String descricao = scanner.nextLine();

        try {
            consultaController.realizar(idConsulta);
            System.out.println("Consulta realizada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private void atualizarDados(Medico medico) {
        System.out.print("Novo nome (enter para manter): ");
        String nome = scanner.nextLine();

        System.out.print("Nova especialidade (enter para manter): ");
        String especialidade = scanner.nextLine();

        System.out.print("Novo plano atendido (enter para manter): ");
        String plano = scanner.nextLine();

        if (!nome.isBlank()) medico.setNome(nome);
        if (!especialidade.isBlank()) medico.setEspecialidade(especialidade);
        if (!plano.isBlank()) medico.setPlanoAtendido(plano);

        medicoController.atualizar(medico);
        System.out.println("Dados atualizados com sucesso.");
    }
}