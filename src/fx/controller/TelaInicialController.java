package fx.controller;

import fx.util.ScreenManager;
import javafx.fxml.FXML;

public class TelaInicialController {

    @FXML
    private void onPaciente() {
        ScreenManager.changeScreen("/view/login-paciente.fxml");
    }

    @FXML
    private void onMedico() {
        ScreenManager.changeScreen("/view/login-medico.fxml");
    }
}
