package cli.consulta;

import controllers.ConsultaController;
import models.entities.Consulta;

import java.util.List;
import java.util.Scanner;

public class MenuConsultaPaciente {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsultaController controller;

    public MenuConsultaPaciente(ConsultaController controller) {
        this.controller = controller;
    }

    public void abrir(int idPaciente) {
        while (true) {
            System.out.println("\n--- CONSULTAS DO PACIENTE ---");
            System.out.println("1 - Agendar consulta");
            System.out.println("2 - Cancelar consulta");
            System.out.println("3 - Minhas consultas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> agendar(idPaciente);
                case 2 -> cancelar();
                case 3 -> listar(idPaciente);
                case 0 -> { return; }
            }
        }
    }

    private void agendar(int idPaciente) {
        System.out.print("ID do médico: ");
        int idMedico = Integer.parseInt(scanner.nextLine());

        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();

        try {
            controller.agendar(idPaciente, idMedico, data);
            System.out.println("Consulta agendada!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelar() {
        System.out.print("ID da consulta: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            controller.cancelar(id);
            System.out.println("Consulta cancelada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar(int idPaciente) {
        List<Consulta> consultas = controller.listarPorPaciente(idPaciente);

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada.");
            return;
        }

        consultas.forEach(c ->
                System.out.println(
                        "ID: " + c.getId() +
                                " | Médico: " + c.getIdMedico() +
                                " | Data: " + c.getData() +
                                " | Status: " + c.getStatus()
                )
        );
    }
}
