package cli.medico;

import controllers.MedicoController;
import models.entities.Medico;

import java.util.Scanner;

public class MenuMedicoLogin {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoController controller;
    private final MenuMedicoLogado menuLogado;

    public MenuMedicoLogin(
            MedicoController controller,
            MenuMedicoLogado menuLogado
    ) {
        this.controller = controller;
        this.menuLogado = menuLogado;
    }

    public void abrir() {
        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();

        Medico medico = controller.login(nome);

        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.println("Bem-vindo, Dr(a). " + medico.getNome());
        menuLogado.abrir(medico);
    }
}
