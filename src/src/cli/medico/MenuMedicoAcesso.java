package cli.medico;

import java.util.Scanner;

public class MenuMedicoAcesso {

    private final Scanner scanner = new Scanner(System.in);
    private final MenuMedicoCadastro menuCadastro;
    private final MenuMedicoLogin menuLogin;

    public MenuMedicoAcesso(
            MenuMedicoCadastro menuCadastro,
            MenuMedicoLogin menuLogin
    ) {
        this.menuCadastro = menuCadastro;
        this.menuLogin = menuLogin;
    }

    public void abrir() {
        while (true) {
            System.out.println("\n--- MÉDICO ---");
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
