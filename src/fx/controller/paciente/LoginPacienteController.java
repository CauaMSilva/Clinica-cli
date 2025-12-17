package fx.controller.paciente;

import controllers.PacienteController;
import fx.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.entities.Paciente;
import repository.implementation.PacienteFileRepository;
import repository.interfaces.PacienteRepository;
import services.implementation.PacienteServiceImpl;
import services.interfaces.PacienteService;
import fx.session.Sessao;

public class LoginPacienteController {

    @FXML
    private TextField txtNome, txtIdade;

    @FXML
    private ComboBox<String> cbPlanoSaude;

    private PacienteController pacienteController;

    public LoginPacienteController() {
        PacienteRepository pacienteRepository = new PacienteFileRepository();
        PacienteService pacienteService = new PacienteServiceImpl(pacienteRepository);
        this.pacienteController = new PacienteController(pacienteService);
    }

    @FXML
    public void initialize() {
        cbPlanoSaude.getItems().addAll(
                "Hapvida",
                "Unimed",
                "Amil",
                "SulAmerica",
                "Não tem"
        );
    }

    private Paciente criarPaciente(String nome, int idade, String planoSaude) {
        int id = pacienteController.gerarNovoId();
        return new Paciente(id, nome, idade, planoSaude);
    }

    @FXML
    private void onCadastro() {
        String nome = txtNome.getText();
        String idadeTexto = txtIdade.getText();
        String planoSaude = cbPlanoSaude.getValue();

        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório");
            return;
        }

        if (idadeTexto == null || idadeTexto.isBlank()) {
            System.out.println("Idade é obrigatória");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeTexto);
        } catch (NumberFormatException e) {
            System.out.println("Idade inválida. Digite apenas números.");
            return;
        }

        if (idade <= 0) {
            System.out.println("Idade deve ser maior que zero");
            return;
        }

        if (planoSaude == null) {
            System.out.println("Selecione um plano de saúde");
            return;
        }

        pacienteController.cadastrarPaciente(
                criarPaciente(nome, idade, planoSaude)
        );

        System.out.println("Paciente cadastrado com sucesso!");

        // Limpa os campos
        txtNome.clear();
        txtIdade.clear();
        cbPlanoSaude.getSelectionModel().clearSelection();
    }

    @FXML
    private void onLogin() {
        String nome = txtNome.getText();

        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório");
            return;
        }

        Paciente paciente = pacienteController.login(nome);

        if (paciente == null) {
            System.out.println("Paciente não cadastrado.");
            return;
        }

        Sessao.setPacienteLogado(paciente);

        System.out.println("Paciente logado: " + paciente.getNome());

        ScreenManager.changeScreen("/view/menu-paciente.fxml");
    }
}
