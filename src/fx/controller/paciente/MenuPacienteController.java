package fx.controller.paciente;

import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.fxml.FXML;
import models.entities.Paciente;

public class MenuPacienteController {

    private Paciente paciente;

    @FXML
    public void initialize() {
        paciente = Sessao.getPacienteLogado();

        if (paciente == null) {
            System.out.println("Sessão inválida.");
            return;
        }

        System.out.println("Paciente logado: " + paciente.getNome());
    }

    @FXML
    private void onListarMedicos() {
        ScreenManager.changeScreen("/view/listar-medicos.fxml");
        System.out.println("Listar médicos");
    }

    @FXML
    private void onListarConsultas() {
        System.out.println("Minhas consultas");
        ScreenManager.changeScreen("/view/minhas-consulta.fxml");
    }

    @FXML
    private void onVoltar() {
        Sessao.logoutPaciente();
        System.out.println("Paciente deslogado");
        ScreenManager.changeScreen("/view/tela-inicial.fxml");
    }
}
