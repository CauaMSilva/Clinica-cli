package controllers;

import models.entities.Paciente;
import services.interfaces.PacienteService;

import java.util.List;

public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void cadastrarPaciente(Paciente paciente) {
        pacienteService.cadastrar(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteService.listar();
    }

    public Paciente buscarPorId(int id) { return pacienteService.buscarPorId(id); }

    public void atualizarPaciente(Paciente paciente) { pacienteService.atualizar(paciente); }

    public void deletarPaciente(int id) { pacienteService.deletar(id); }

    public int gerarNovoId(){ return pacienteService.gerarNovoId(); }

    public Paciente login(String nome) { return pacienteService.login(nome); }

}
