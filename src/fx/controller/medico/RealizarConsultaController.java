package fx.controller.medico;

import controllers.ConsultaController;
import controllers.PacienteController;
import fx.session.Sessao;
import fx.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;

import java.util.List;

public class RealizarConsultaController {

    @FXML
    private TableView<Consulta> tableConsultas;

    @FXML
    private TableColumn<Consulta, String> colPaciente;

    @FXML
    private TableColumn<Consulta, String> colData;

    @FXML
    private TableColumn<Consulta, String> colStatus;

    @FXML
    private TextArea txtComentario;

    @FXML
    private Label lblMensagem;

    private ConsultaController consultaController;
    private PacienteController pacienteController;
    private Medico medicoLogado;

    public RealizarConsultaController() {
        consultaController = new ConsultaController(new services.implementation.ConsultaServiceImpl(
                new repository.implementation.ConsultaFileRepository()
        ));
        pacienteController = new PacienteController(new services.implementation.PacienteServiceImpl(
                new repository.implementation.PacienteFileRepository()
        ));
    }

    @FXML
    public void initialize() {
        medicoLogado = Sessao.getMedicoLogado();

        colPaciente.setCellValueFactory(cellData -> {
            Paciente p = pacienteController.buscarPorId(cellData.getValue().getIdPaciente());
            return new javafx.beans.property.SimpleStringProperty(p != null ? p.getNome() : "Desconhecido");
        });

        colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData()));
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        carregarConsultas();
    }

    private void carregarConsultas() {
        List<Consulta> consultas = consultaController.listarPorMedico(medicoLogado.getId());
        List<Consulta> agendadas = consultas.stream()
                .filter(c -> c.getStatus().equalsIgnoreCase("AGENDADA"))
                .toList();
        tableConsultas.setItems(FXCollections.observableArrayList(agendadas));
    }

    @FXML
    private void onFinalizar() {
        Consulta consultaSelecionada = tableConsultas.getSelectionModel().getSelectedItem();
        if (consultaSelecionada == null) {
            lblMensagem.setText("Selecione uma consulta para finalizar!");
            return;
        }

        String comentario = txtComentario.getText();
        if (comentario == null) comentario = "";

        // Atualiza a consulta
        consultaSelecionada.setStatus("REALIZADA");
        consultaSelecionada.setComentarioMedico(comentario);
        consultaController.atualizarConsulta(consultaSelecionada);

        // Simula a cobrança
        Paciente paciente = pacienteController.buscarPorId(consultaSelecionada.getIdPaciente());
        if (paciente != null && (paciente.getPlanoSaude().equals("Não tem") || paciente.getPlanoSaude().isBlank())) {
            double valorConsulta = calcularValorConsulta(medicoLogado.getEspecialidade());
            lblMensagem.setText("Consulta finalizada! Valor a pagar: R$ " + valorConsulta);
            System.out.println("Conta gerada para paciente " + paciente.getNome() + ": R$ " + valorConsulta);
        } else {
            lblMensagem.setText("Consulta finalizada com sucesso!");
        }

        carregarConsultas();
        txtComentario.clear();
    }

    @FXML
    private void onVoltar() {
        ScreenManager.changeScreen("/view/menu-medico.fxml");
    }

    private double calcularValorConsulta(String especialidade) {
        return switch (especialidade) {
            case "Clínico Geral" -> 100;
            case "Cardiologia" -> 200;
            case "Pediatria" -> 150;
            case "Ortopedia" -> 180;
            case "Neurologia" -> 250;
            default -> 100;
        };
    }
}
