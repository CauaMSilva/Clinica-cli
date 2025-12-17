package fx.controller.paciente;

import controllers.AvaliacaoController;
import controllers.MedicoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.entities.Medico;
import models.entities.Paciente;
import repository.implementation.AvaliacaoFileRepository;
import repository.implementation.ConsultaFileRepository;
import repository.implementation.MedicoFileRepository;
import repository.interfaces.AvaliacaoRepository;
import repository.interfaces.ConsultaRepository;
import repository.interfaces.MedicoRepository;
import services.implementation.AvaliacaoServiceImpl;
import services.implementation.MedicoServiceImpl;
import services.interfaces.AvaliacaoService;
import services.interfaces.MedicoService;

import java.util.List;
import java.util.stream.Collectors;

public class ListarMedicosController {

    @FXML
    private TableView<Medico> tableMedicos;

    @FXML
    private TableColumn<Medico, String> colId;

    @FXML
    private TableColumn<Medico, String> colNome;

    @FXML
    private TableColumn<Medico, String> colEspecialidade;

    @FXML
    private TableColumn<Medico, String> colPlano;

    @FXML
    private TableColumn<Medico, String> colEstrelas;

    private MedicoController medicoController;
    private AvaliacaoController avaliacaoController;
    private Paciente pacienteLogado;

    public ListarMedicosController() {
        MedicoRepository medicoRepository = new MedicoFileRepository();
        MedicoService medicoService = new MedicoServiceImpl(medicoRepository);
        this.medicoController = new MedicoController(medicoService);

        AvaliacaoRepository avaliacaoRepository = new AvaliacaoFileRepository();
        ConsultaRepository consultaRepository = new ConsultaFileRepository();
        AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository, consultaRepository);
        this.avaliacaoController = new AvaliacaoController(avaliacaoService);
    }

    @FXML
    public void initialize() {
        pacienteLogado = Sessao.getPacienteLogado();

        colId.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome())
        );
        colEspecialidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEspecialidade())
        );
        colPlano.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlanoAtendido())
        );
        colEstrelas.setCellValueFactory(cellData -> {
            Medico medico = cellData.getValue();
            double media = avaliacaoController.mediaEstrelas(medico.getId());
            String estrelas = String.format("%.1f ⭐", media);
            return new SimpleStringProperty(estrelas);
        });

        carregarMedicos();
    }

    private void carregarMedicos() {
        List<Medico> todosMedicos = medicoController.listarMedicos();

        List<Medico> medicosFiltrados = todosMedicos.stream()
                .filter(m -> m.getPlanoAtendido().equalsIgnoreCase(pacienteLogado.getPlanoSaude()))
                .collect(Collectors.toList());

        ObservableList<Medico> observableList;

        if (!medicosFiltrados.isEmpty()) {
            observableList = FXCollections.observableArrayList(medicosFiltrados);
        } else {
            observableList = FXCollections.observableArrayList(todosMedicos);
        }

        tableMedicos.setItems(observableList);
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-paciente.fxml");
        System.out.println("Voltando para o menu");
    }
}
