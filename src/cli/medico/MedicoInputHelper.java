package cli.medico;

import controllers.MedicoController;
import models.entities.Medico;

import java.util.Scanner;

public class MedicoInputHelper {

    private final Scanner scanner;
    private final MedicoController controller;

    public MedicoInputHelper(Scanner scanner, MedicoController controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public Medico lerNovoMedico() {
        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Plano atendido: ");
        String plano = scanner.nextLine();

        int id = controller.gerarNovoId();

        return new Medico(id, nome, especialidade, plano);
    }

    public int lerIdMedico() {
        System.out.print("ID: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
