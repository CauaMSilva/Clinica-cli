package cli.consulta;

import controllers.ConsultaController;
import models.entities.Consulta;

import java.util.Scanner;

public class ConsultaInputHelper {

    private final Scanner scanner;
    private final ConsultaController controller;

    public ConsultaInputHelper(Scanner scanner, ConsultaController controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public Consulta lerNovaConsulta() {
        System.out.print("ID do paciente: ");
        int idPaciente = Integer.parseInt(scanner.nextLine());

        System.out.print("ID do médico: ");
        int idMedico = Integer.parseInt(scanner.nextLine());

        System.out.print("Data da consulta (ex: 20/12/2025): ");
        String data = scanner.nextLine();

        int id = controller.gerarNovoId();

        return new Consulta(id, idPaciente, idMedico, data, "AGENDADA");
    }

    public int lerIdConsulta() {
        System.out.print("ID da consulta: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
