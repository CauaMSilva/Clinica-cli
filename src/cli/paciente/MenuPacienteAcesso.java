package cli.paciente;

import java.util.Scanner;

public class MenuPacienteAcesso {

    private final Scanner scanner = new Scanner(System.in);

    private final MenuPacienteCadastro menuCadastro;
    private final MenuPacienteLogin menuLogin;

    public MenuPacienteAcesso(
            MenuPacienteCadastro menuCadastro,
            MenuPacienteLogin menuLogin
    ) {
        this.menuCadastro = menuCadastro;
        this.menuLogin = menuLogin;
    }

    public void abrir() {
        while (true) {
            System.out.println("\n--- PACIENTE ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> menuCadastro.abrir();
                case 2 -> menuLogin.abrir();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
