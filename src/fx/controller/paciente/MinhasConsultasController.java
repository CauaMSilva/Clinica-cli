package fx.controller.paciente;

import controllers.ConsultaController;
import controllers.MedicoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;
import repository.implementation.ConsultaFileRepository;
import repository.implementation.MedicoFileRepository;
import repository.interfaces.ConsultaRepository;
import repository.interfaces.MedicoRepository;
import services.implementation.ConsultaServiceImpl;
import services.implementation.MedicoServiceImpl;
import services.interfaces.ConsultaService;
import services.interfaces.MedicoService;

import java.util.List;

public class MinhasConsultasController {

    @FXML
    private TableView<Consulta> tableConsultas;

    @FXML
    private TableColumn<Consulta, String> colId;      // Coluna de ID

    @FXML
    private TableColumn<Consulta, String> colMedico;

    @FXML
    private TableColumn<Consulta, String> colData;

    @FXML
    private TableColumn<Consulta, String> colStatus;

    private ConsultaController consultaController;
    private MedicoController medicoController;
    private Paciente pacienteLogado;

    public MinhasConsultasController() {
        ConsultaRepository consultaRepository = new ConsultaFileRepository();
        ConsultaService consultaService = new ConsultaServiceImpl(consultaRepository);
        this.consultaController = new ConsultaController(consultaService);

        MedicoRepository medicoRepository = new MedicoFileRepository();
        MedicoService medicoService = new MedicoServiceImpl(medicoRepository);
        this.medicoController = new MedicoController(medicoService);
    }

    @FXML
    public void initialize() {
        pacienteLogado = Sessao.getPacienteLogado();

        // Coluna ID
        colId.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        // Coluna Médico
        colMedico.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = medicoController.buscarPorId(consulta.getIdMedico());
            String nome = medico != null ? medico.getNome() : "Desconhecido";
            return new SimpleStringProperty(nome);
        });

        // Coluna Data
        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getData())
        );

        // Coluna Status
        colStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus())
        );

        carregarConsultas();
    }

    private void carregarConsultas() {
        List<Consulta> consultas =
                consultaController.listarPorPaciente(pacienteLogado.getId());

        tableConsultas.setItems(
                FXCollections.observableArrayList(consultas)
        );
    }

    @FXML
    private void onMarcar() {
        ScreenManager.changeScreen("/view/marcar-consulta.fxml");
    }

    @FXML
    private void onCancelar() {
        Consulta selecionada =
                tableConsultas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            System.out.println("Selecione uma consulta");
            return;
        }

        if (!"AGENDADA".equals(selecionada.getStatus())) {
            System.out.println("Só consultas marcadas podem ser canceladas");
            return;
        }

        consultaController.cancelar(selecionada.getId());
        carregarConsultas();
        System.out.println("Consulta cancelada com sucesso");
    }

    @FXML
    private void onAvaliar() {
        Consulta selecionada =
                tableConsultas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            System.out.println("Selecione uma consulta");
            return;
        }

        if (!"REALIZADA".equals(selecionada.getStatus())) {
            System.out.println("Só consultas realizadas podem ser avaliadas");
            return;
        }

        Sessao.setConsultaSelecionada(selecionada);
        ScreenManager.changeScreen("/view/avaliar-consulta.fxml");
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-paciente.fxml");
    }
}
