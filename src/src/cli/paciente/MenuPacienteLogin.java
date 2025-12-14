package cli.paciente;

import controllers.PacienteController;
import models.entities.Paciente;

import java.util.Scanner;

public class MenuPacienteLogin {

    private final Scanner scanner = new Scanner(System.in);
    private final PacienteController controller;
    private final MenuPacienteLogado menuLogado;

    public MenuPacienteLogin(
            PacienteController controller,
            MenuPacienteLogado menuLogado
    ) {
        this.controller = controller;
        this.menuLogado = menuLogado;
    }

    public void abrir() {
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();

        Paciente paciente = controller.login(nome);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("Bem-vindo, " + paciente.getNome() + "!");
        menuLogado.abrir(paciente);
    }
}
