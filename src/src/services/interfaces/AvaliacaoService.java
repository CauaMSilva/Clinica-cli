package services.interfaces;


import models.entities.Avaliacao;

import java.util.List;

public interface AvaliacaoService {

    void avaliar(int id, int idConsulta, int idMedico, int estrelas, String comentario);

    int gerarNovoId();

    double mediaEstrelas(int idMedico);
}
