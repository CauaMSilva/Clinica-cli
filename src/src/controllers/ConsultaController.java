package controllers;

import models.entities.Consulta;
import services.interfaces.ConsultaService;

import java.util.List;

public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) { this.service = service; }

    public void agendar(int idPaciente, int idMedico, String data) { service.agendar(idPaciente, idMedico, data); }

    public void cancelar(int idConsulta) { service.cancelar(idConsulta); }

    public void realizar(int idConsulta) { service.realizar(idConsulta); }

    public List<Consulta> listarPorPaciente(int idPaciente) { return service.listarPorPaciente(idPaciente); }

    public List<Consulta> listarPorMedico(int idMedico) { return service.listarPorMedico(idMedico); }

    public int gerarNovoId(){ return service.gerarNovoId(); }

    public Consulta buscarPorId(int idConsulta) { return service.buscarPorId(idConsulta); }
}
