package fx.controller.medico;

import controllers.MedicoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.entities.Medico;
import repository.implementation.MedicoFileRepository;
import repository.interfaces.MedicoRepository;
import services.implementation.MedicoServiceImpl;
import services.interfaces.MedicoService;

public class LoginMedicoController {

    @FXML
    private TextField txtNome;

    @FXML
    private ComboBox<String> cbEspecialidade;

    @FXML
    private ComboBox<String> cbPlanoSaude;

    private MedicoController medicoController;

    public LoginMedicoController() {
        MedicoRepository medicoRepository = new MedicoFileRepository();
        MedicoService medicoService = new MedicoServiceImpl(medicoRepository);
        this.medicoController = new MedicoController(medicoService);
    }

    @FXML
    public void initialize() {
        cbEspecialidade.getItems().addAll(
                "Clínico Geral",
                "Cardiologia",
                "Pediatria",
                "Ortopedia",
                "Neurologia"
        );

        cbPlanoSaude.getItems().addAll(
                "Hapvida",
                "Unimed",
                "Amil",
                "SulAmerica"
        );
    }

    private Medico lerMedico(String nome, String especialidade, String planoSaude) {
        int id = medicoController.gerarNovoId();
        return new Medico(id, nome, especialidade, planoSaude);
    }

    @FXML
    private void onCadastro() {
        String nome = txtNome.getText();
        String especialidade = cbEspecialidade.getValue();
        String planoSaude = cbPlanoSaude.getValue();

        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório");
            return;
        }

        if (especialidade == null) {
            System.out.println("Selecione a especialidade");
            return;
        }

        if (planoSaude == null) {
            System.out.println("Selecione o plano de saúde");
            return;
        }

        medicoController.cadastrarMedico(
                lerMedico(nome, especialidade, planoSaude)
        );

        System.out.println("Médico cadastrado com sucesso!");

        txtNome.clear();
        cbEspecialidade.getSelectionModel().clearSelection();
        cbPlanoSaude.getSelectionModel().clearSelection();
    }

    @FXML
    private void onLogin() {
        String nome = txtNome.getText();

        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório");
            return;
        }

        Medico medico = medicoController.login(nome);

        if (medico == null) {
            System.out.println("Médico não cadastrado.");
            return;
        }

        System.out.println("Médico logado: " + medico.getNome());

        Sessao.setMedicoLogado(medico);
        ScreenManager.changeScreen("/view/menu-medico.fxml");
    }
}
