package controllers;

import models.entities.Consulta;
import models.entities.Paciente;
import services.interfaces.ConsultaService;

import java.util.List;

public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) { this.service = service; }

    public void agendar(int idPaciente, int idMedico, String data, String comentario) { service.agendar(idPaciente, idMedico, data, comentario); }

    public void cancelar(int idConsulta) { service.cancelar(idConsulta); }

    public void realizar(int idConsulta) { service.realizar(idConsulta); }

    public List<Consulta> listarPorPaciente(int idPaciente) { return service.listarPorPaciente(idPaciente); }

    public List<Consulta> listarPorMedico(int idMedico) { return service.listarPorMedico(idMedico); }

    public int gerarNovoId(){ return service.gerarNovoId(); }

    public Consulta buscarPorId(int idConsulta) { return service.buscarPorId(idConsulta); }

    public int contarConsultasPorMedicoEData(int idMedico, String data) {
        List<Consulta> todas = service.listarPorMedico(idMedico);
        return (int) todas.stream()
                .filter(c -> c.getData().equals(data))
                .count();
    }

    public void atualizarConsulta(Consulta consulta) {
        service.atualizar(consulta);
    }

    public Paciente buscarPacientePorId(int idPaciente) {
        return service.buscarPacientePorId(idPaciente);
    }
}
