package cli;

import cli.medico.*;
import cli.paciente.*;
import controllers.*;
import config.AppConfig;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner = new Scanner(System.in);

    private final MenuPacienteAcesso menuPaciente;
    private final MenuMedicoAcesso menuMedico;


    public MenuPrincipal(AppConfig config) {
        this.menuPaciente = new MenuPacienteAcesso(config.pacienteCadastro(), config.pacienteLogin());
        this.menuMedico = new MenuMedicoAcesso(config.medicoCadastro(), config.medicoLogin());
    }

    public void abrir() {
        while (true) {
            System.out.println("\n=== SISTEMA CLÍNICA ===");
            System.out.println("1 - Paciente");
            System.out.println("2 - Médico");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> menuPaciente.abrir();
                case 2 -> menuMedico.abrir();
                case 0 -> {
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
