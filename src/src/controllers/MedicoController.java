package controllers;

import models.entities.Medico;
import services.interfaces.MedicoService;

import java.util.List;

public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    public void cadastrarMedico(Medico medico) {
        medicoService.cadastrar(medico);
    }

    public List<Medico> listarMedicos() {
        return medicoService.listar();
    }

    public int gerarNovoId(){ return medicoService.gerarNovoId(); }

    public Medico buscarPorId(int id){ return medicoService.buscarPorId(id); }

    public List<Medico> listarPorPlano(String plano) { return medicoService.listarPorPlano(plano); }

    public Medico login(String nome) { return medicoService.login(nome); }

    public void atualizar(Medico medico){ medicoService.atualizar(medico); }
}
