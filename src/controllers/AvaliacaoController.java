package controllers;

import models.entities.Avaliacao;
import services.interfaces.AvaliacaoService;

import java.util.List;

public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    public void avaliar(int id, int idConsulta, int idMedico, int estrelas, String comentario) {
        service.avaliar(id, idConsulta, idMedico, estrelas, comentario);
    }

    public int gerarNovoId(){ return service.gerarNovoId(); }

    public double mediaEstrelas(int idMedico) { return service.mediaEstrelas(idMedico); }

}
