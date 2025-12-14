package cli.paciente;

import exceptions.arquivoException;
import models.entities.Paciente;
import controllers.PacienteController;

import java.util.List;
import java.util.Scanner;

public class PacienteInputHelper {

    private final Scanner scanner;
    private final PacienteController controller;

    public PacienteInputHelper(Scanner scanner, PacienteController controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public Paciente lerNovoPaciente() {
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        if (idade < 0) { throw new IllegalArgumentException("Idade não pode ser negativa"); }

        System.out.print("Plano de saúde(se não tiver, deixe em branco): ");
        String plano = scanner.nextLine();

        if (plano.isBlank()) { plano = "SEM_PLANO"; }

        int id = controller.gerarNovoId();

        return new Paciente(id, nome, idade, plano);
    }

    public int lerIdPaciente() {
        System.out.print("ID: ");
        return Integer.parseInt(scanner.nextLine());
    }
}