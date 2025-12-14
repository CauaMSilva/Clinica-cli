package cli.receita;

import controllers.ReceitaController;
import models.entities.Receita;

import java.util.Scanner;

public class ReceitaInputHelper {

    private final Scanner scanner;
    private final ReceitaController controller;

    public ReceitaInputHelper(Scanner scanner, ReceitaController controller) {
        this.scanner = scanner;
        this.controller = controller;
    }

    public Receita lerNovaReceita() {
        System.out.print("ID do paciente: ");
        int idPaciente = Integer.parseInt(scanner.nextLine());

        System.out.print("ID do médico: ");
        int idMedico = Integer.parseInt(scanner.nextLine());

        System.out.print("Descrição / medicamentos: ");
        String descricao = scanner.nextLine();

        System.out.print("Data da receita: ");
        String data = scanner.nextLine();

        int id = controller.gerarNovoId();

        return new Receita(id, idPaciente, idMedico, descricao, data);
    }

    public int lerIdReceita() {
        System.out.print("ID da receita: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
