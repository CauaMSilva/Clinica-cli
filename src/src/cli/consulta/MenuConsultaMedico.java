package cli.consulta;

import controllers.ConsultaController;
import models.entities.Consulta;

import java.util.List;
import java.util.Scanner;

public class MenuConsultaMedico {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsultaController controller;

    public MenuConsultaMedico(ConsultaController controller) {
        this.controller = controller;
    }

    public void abrir(int idMedico) {
        while (true) {
            System.out.println("\n--- CONSULTAS DO MÉDICO ---");
            System.out.println("1 - Ver consultas");
            System.out.println("2 - Realizar consulta");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> listar(idMedico);
                case 2 -> realizar();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listar(int idMedico) {
        List<Consulta> consultas = controller.listarPorMedico(idMedico);

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada.");
            return;
        }

        for (Consulta c : consultas) {
            System.out.println(
                    "ID: " + c.getId() +
                            " | Paciente: " + c.getIdPaciente() +
                            " | Data: " + c.getData() +
                            " | Status: " + c.getStatus()
            );
        }
    }

    private void realizar() {
        System.out.print("ID da consulta: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Descrição da consulta: ");
        String descricao = scanner.nextLine();

        try {
            controller.realizar(id);
            System.out.println("Consulta realizada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
