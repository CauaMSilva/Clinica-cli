package fx.controller.medico;

import fx.util.ScreenManager;
import javafx.fxml.FXML;

public class MenuMedicoController {

    @FXML
    private void onMinhasConsultas() {
        ScreenManager.changeScreen("/view/medico-minhas-consultas.fxml");
        System.out.println("Abrindo tela de consultas do médico...");
    }

    @FXML
    private void onRealizarConsulta() {
        ScreenManager.changeScreen("/view/medico-realizar-consulta.fxml");
        System.out.println("Abrindo tela de realizar consulta...");
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/tela-inicial.fxml");
        System.out.println("Voltando para menu inicial...");
    }
}
