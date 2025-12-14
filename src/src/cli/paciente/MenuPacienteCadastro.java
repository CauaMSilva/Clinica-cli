package cli.paciente;

import controllers.PacienteController;
import models.entities.Paciente;

public class MenuPacienteCadastro {

    private final PacienteController controller;
    private final PacienteInputHelper input;

    public MenuPacienteCadastro(
            PacienteController controller,
            PacienteInputHelper input
    ) {
        this.controller = controller;
        this.input = input;
    }

    public void abrir() {
        Paciente paciente = input.lerNovoPaciente();
        controller.cadastrarPaciente(paciente);
        System.out.println("Paciente cadastrado com sucesso!");
    }
}
