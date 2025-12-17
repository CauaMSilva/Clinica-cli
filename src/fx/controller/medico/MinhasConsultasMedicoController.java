package fx.controller.medico;

import controllers.ConsultaController;
import controllers.PacienteController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;
import repository.implementation.ConsultaFileRepository;
import repository.implementation.PacienteFileRepository;
import repository.interfaces.ConsultaRepository;
import repository.interfaces.PacienteRepository;
import services.implementation.ConsultaServiceImpl;
import services.implementation.PacienteServiceImpl;
import services.interfaces.ConsultaService;
import services.interfaces.PacienteService;

import java.util.List;

public class MinhasConsultasMedicoController {

    @FXML
    private TableView<Consulta> tableConsultas;

    @FXML
    private TableColumn<Consulta, String> colId;

    @FXML
    private TableColumn<Consulta, String> colPaciente;

    @FXML
    private TableColumn<Consulta, String> colData;

    @FXML
    private TableColumn<Consulta, String> colStatus;

    @FXML
    private Label lblMensagem;

    private ConsultaController consultaController;
    private PacienteController pacienteController;
    private Medico medicoLogado;

    public MinhasConsultasMedicoController() {
        ConsultaRepository consultaRepository = new ConsultaFileRepository();
        ConsultaService consultaService = new ConsultaServiceImpl(consultaRepository);
        this.consultaController = new ConsultaController(consultaService);

        PacienteRepository pacienteRepository = new PacienteFileRepository();
        PacienteService pacienteService = new PacienteServiceImpl(pacienteRepository);
        this.pacienteController = new PacienteController(pacienteService);
    }

    @FXML
    public void initialize() {
        medicoLogado = Sessao.getMedicoLogado();

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colPaciente.setCellValueFactory(cellData -> {
            Paciente p = pacienteController.buscarPorId(cellData.getValue().getIdPaciente());
            return new SimpleStringProperty(p != null ? p.getNome() : "Desconhecido");
        });
        colData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        carregarConsultas();
    }

    private void carregarConsultas() {
        List<Consulta> consultas = consultaController.listarPorMedico(medicoLogado.getId());
        tableConsultas.setItems(FXCollections.observableArrayList(consultas));
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-medico.fxml");
    }
}
