package fx.controller.paciente;

import controllers.PacienteController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.entities.Paciente;

public class AtualizarDadosPacienteController {

    @FXML
    private TextField txtNome, txtIdade;

    @FXML
    private ComboBox<String> cbPlanoSaude;

    @FXML
    private Label lblMensagem;

    private PacienteController pacienteController;
    private Paciente pacienteLogado;

    public AtualizarDadosPacienteController() {
        pacienteController = new PacienteController(new services.implementation.PacienteServiceImpl(
                new repository.implementation.PacienteFileRepository()
        ));
    }

    @FXML
    public void initialize() {
        pacienteLogado = Sessao.getPacienteLogado();

        txtNome.setText(pacienteLogado.getNome());
        txtIdade.setText(String.valueOf(pacienteLogado.getIdade()));
        cbPlanoSaude.setItems(FXCollections.observableArrayList(
                "Hapvida", "Unimed", "Amil", "SulAmerica", "Não tem"
        ));
        cbPlanoSaude.setValue(pacienteLogado.getPlanoSaude());
    }

    @FXML
    private void onAtualizar() {
        String nome = txtNome.getText();
        String idadeStr = txtIdade.getText();
        String plano = cbPlanoSaude.getValue();

        if (nome == null || nome.isBlank() || idadeStr == null || idadeStr.isBlank() || plano == null) {
            lblMensagem.setText("Preencha todos os campos!");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) {
            lblMensagem.setText("Idade inválida!");
            return;
        }

        pacienteLogado.setNome(nome);
        pacienteLogado.setIdade(idade);
        pacienteLogado.setPlanoSaude(plano);

        pacienteController.atualizarPaciente(pacienteLogado);
        Sessao.setPacienteLogado(pacienteLogado);

        lblMensagem.setText("Dados atualizados com sucesso!");
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-paciente.fxml");
    }
}
