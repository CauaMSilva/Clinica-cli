package fx.controller.medico;

import controllers.MedicoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.entities.Medico;

public class AtualizarDadosMedicoController {

    @FXML
    private TextField txtNome;

    @FXML
    private ComboBox<String> cbEspecialidade, cbPlanoSaude;

    @FXML
    private Label lblMensagem;

    private MedicoController medicoController;
    private Medico medicoLogado;

    public AtualizarDadosMedicoController() {
        medicoController = new MedicoController(new services.implementation.MedicoServiceImpl(
                new repository.implementation.MedicoFileRepository()
        ));
    }

    @FXML
    public void initialize() {
        medicoLogado = Sessao.getMedicoLogado();

        txtNome.setText(medicoLogado.getNome());

        cbEspecialidade.setItems(FXCollections.observableArrayList(
                "Clínico Geral", "Cardiologia", "Pediatria", "Ortopedia", "Neurologia"
        ));
        cbEspecialidade.setValue(medicoLogado.getEspecialidade());

        cbPlanoSaude.setItems(FXCollections.observableArrayList(
                "Hapvida", "Unimed", "Amil", "SulAmerica", "Omint"
        ));
        cbPlanoSaude.setValue(medicoLogado.getPlanoAtendido());
    }

    @FXML
    private void onAtualizar() {
        String nome = txtNome.getText();
        String especialidade = cbEspecialidade.getValue();
        String plano = cbPlanoSaude.getValue();

        if (nome == null || nome.isBlank() || especialidade == null || plano == null) {
            lblMensagem.setText("Preencha todos os campos!");
            return;
        }

        medicoLogado.setNome(nome);
        medicoLogado.setEspecialidade(especialidade);
        medicoLogado.setPlanoAtendido(plano);

        medicoController.atualizar(medicoLogado);
        Sessao.setMedicoLogado(medicoLogado);

        lblMensagem.setText("Dados atualizados com sucesso!");
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-medico.fxml");
    }
}
