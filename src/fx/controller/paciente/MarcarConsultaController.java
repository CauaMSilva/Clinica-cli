package fx.controller.paciente;

import controllers.ConsultaController;
import controllers.MedicoController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MarcarConsultaController {

    @FXML
    private ComboBox<Medico> cbMedicos;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lblMensagem;

    private MedicoController medicoController;
    private ConsultaController consultaController;
    private Paciente pacienteLogado;

    public MarcarConsultaController() {
        MedicoRepository medicoRepository = new MedicoFileRepository();
        MedicoService medicoService = new MedicoServiceImpl(medicoRepository);
        this.medicoController = new MedicoController(medicoService);

        ConsultaRepository consultaRepository = new ConsultaFileRepository();
        ConsultaService consultaService = new ConsultaServiceImpl(consultaRepository);
        this.consultaController = new ConsultaController(consultaService);
    }

    @FXML
    public void initialize() {
        pacienteLogado = Sessao.getPacienteLogado();

        List<Medico> todosMedicos = medicoController.listarMedicos();

        List<Medico> medicosFiltrados = todosMedicos.stream()
                .filter(m -> m.getPlanoAtendido().equalsIgnoreCase(pacienteLogado.getPlanoSaude()))
                .toList();

        if (medicosFiltrados.isEmpty()) {
            medicosFiltrados = todosMedicos;
        }

        cbMedicos.setItems(FXCollections.observableArrayList(medicosFiltrados));
        cbMedicos.setPromptText("Selecione um médico");

        cbMedicos.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Medico item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });

        cbMedicos.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Medico item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
    }

    @FXML
    private void onMarcar() {
        Medico medicoSelecionado = cbMedicos.getValue();
        LocalDate data = dpData.getValue();

        if (medicoSelecionado == null) {
            lblMensagem.setText("Selecione um médico!");
            return;
        }
        if (data == null) {
            lblMensagem.setText("Selecione uma data!");
            return;
        }

        if (data.isBefore(LocalDate.now())) {
            lblMensagem.setText("Não é possível marcar consultas em datas passadas!");
            return;
        }

        int consultasPaciente = consultaController.listarPorPaciente(pacienteLogado.getId()).stream()
                .filter(c -> c.getData().equals(data.toString()) && !c.getStatus().equalsIgnoreCase("CANCELADA"))
                .toList().size();

        if (consultasPaciente > 0) {
            lblMensagem.setText("Você já possui uma consulta marcada neste dia!");
            return;
        }

        int consultasNoDia = consultaController.listarPorMedico(medicoSelecionado.getId()).stream()
                .filter(c -> c.getData().equals(data.toString()) && c.getStatus().equalsIgnoreCase("AGENDADA"))
                .toList().size();

        String status;
        if (consultasNoDia < 3) {
            status = "AGENDADO";
        } else {
            status = "ESPERA"; // coloca na fila de espera
        }

        Consulta consulta = new Consulta(
                consultaController.gerarNovoId(),
                pacienteLogado.getId(),
                medicoSelecionado.getId(),
                data.toString(),
                status,
                ""
        );

        consultaController.agendar(
                consulta.getIdPaciente(),
                consulta.getIdMedico(),
                consulta.getData(),
                consulta.getStatus()
        );

        if (status.equals("AGENDADA")) {
            lblMensagem.setText("Consulta marcada com sucesso!");
        } else {
            lblMensagem.setText("Consultas do dia já estão completas. Você foi colocado na fila de espera.");
        }

        cbMedicos.getSelectionModel().clearSelection();
        dpData.setValue(null);
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/minhas-consulta.fxml");
    }
}
