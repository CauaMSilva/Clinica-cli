package controllers;

import models.entities.Receita;
import services.interfaces.ReceitaService;

import java.util.List;

public class ReceitaController {

    private final ReceitaService service;

    public ReceitaController(ReceitaService service) {
        this.service = service;
    }

    public void cadastrarReceita(Receita receita) {
        service.cadastrar(receita);
    }

    public List<Receita> listarReceitas() {
        return service.listar();
    }

    public Receita buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public int gerarNovoId() {
        return service.gerarNovoId();
    }
}
