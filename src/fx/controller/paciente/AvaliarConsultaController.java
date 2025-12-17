package fx.controller.paciente;

import controllers.AvaliacaoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import models.entities.Avaliacao;
import models.entities.Consulta;
import models.entities.Paciente;
import repository.implementation.AvaliacaoFileRepository;
import repository.implementation.ConsultaFileRepository;
import repository.interfaces.AvaliacaoRepository;
import repository.interfaces.ConsultaRepository;
import services.implementation.AvaliacaoServiceImpl;
import services.interfaces.AvaliacaoService;

public class AvaliarConsultaController {

    @FXML
    private ComboBox<Integer> cbEstrelas;

    @FXML
    private TextArea txtComentario;

    @FXML
    private Label lblMensagem;

    private AvaliacaoController avaliacaoController;
    private Consulta consultaSelecionada;
    private Paciente pacienteLogado;

    public AvaliarConsultaController() {
        AvaliacaoRepository avaliacaoRepository = new AvaliacaoFileRepository();
        ConsultaRepository consultaRepository = new ConsultaFileRepository();
        AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository, consultaRepository);
        this.avaliacaoController = new AvaliacaoController(avaliacaoService);
    }

    @FXML
    public void initialize() {
        pacienteLogado = Sessao.getPacienteLogado();
        consultaSelecionada = Sessao.getConsultaSelecionada();

        cbEstrelas.getItems().addAll(1, 2, 3, 4, 5);
        cbEstrelas.setPromptText("Selecione a nota");
    }

    @FXML
    private void onAvaliar() {
        Integer estrelas = cbEstrelas.getValue();
        String comentario = txtComentario.getText();

        if (estrelas == null) {
            lblMensagem.setText("Selecione uma nota!");
            return;
        }

        if (comentario == null || comentario.isBlank()) {
            lblMensagem.setText("Digite um comentário!");
            return;
        }

        Avaliacao avaliacao = new Avaliacao(
                avaliacaoController.gerarNovoId(),
                consultaSelecionada.getId(),
                pacienteLogado.getId(),
                estrelas,
                comentario
        );

        avaliacaoController.avaliar(
                avaliacaoController.gerarNovoId(),
                avaliacao.getIdConsulta(),
                avaliacao.getIdMedico(),
                avaliacao.getNota(),
                avaliacao.getComentario()
        );
        lblMensagem.setText("Avaliação registrada com sucesso!");

        cbEstrelas.getSelectionModel().clearSelection();
        txtComentario.clear();
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/minhas-consulta.fxml");
    }
}
