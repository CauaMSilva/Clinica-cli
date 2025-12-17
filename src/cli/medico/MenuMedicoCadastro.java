package cli.medico;

import controllers.MedicoController;
import models.entities.Medico;

public class MenuMedicoCadastro {

    private final MedicoController controller;
    private final MedicoInputHelper input;

    public MenuMedicoCadastro(
            MedicoController controller,
            MedicoInputHelper input
    ) {
        this.controller = controller;
        this.input = input;
    }

    public void abrir() {
        Medico medico = input.lerNovoMedico();
        controller.cadastrarMedico(medico);
        System.out.println("Médico cadastrado com sucesso!");
    }
}
