package cli.receita;

import controllers.ReceitaController;

import java.util.Scanner;

public class MenuReceita {

    private final Scanner scanner = new Scanner(System.in);
    private final ReceitaController controller;
    private final ReceitaInputHelper input;

    public MenuReceita(ReceitaController controller) {
        this.controller = controller;
        this.input = new ReceitaInputHelper(scanner, controller);
    }

    public void abrir() {
        while (true) {
            System.out.println("\n--- RECEITAS ---");
            System.out.println("1 - Criar receita");
            System.out.println("2 - Listar receitas");
            System.out.println("3 - Buscar receita por ID");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> criar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 0 -> { return; }
            }
        }
    }

    private void criar() {
        try {
            var receita = input.lerNovaReceita();
            controller.cadastrarReceita(receita);
            System.out.println("Receita criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        controller.listarReceitas().forEach(r ->
                System.out.println(
                        "ID: " + r.getId() +
                                " | Paciente: " + r.getIdPaciente() +
                                " | Médico: " + r.getIdMedico() +
                                " | Data: " + r.getData() +
                                " | Descrição: " + r.getDescricao()
                )
        );
    }

    private void buscarPorId() {
        int id = input.lerIdReceita();
        var r = controller.buscarPorId(id);

        if (r == null) {
            System.out.println("Receita não encontrada.");
            return;
        }

        System.out.println(
                "Paciente: " + r.getIdPaciente() +
                        "\nMédico: " + r.getIdMedico() +
                        "\nData: " + r.getData() +
                        "\nDescrição: " + r.getDescricao()
        );
    }
}
