package services.interfaces;


import models.entities.Avaliacao;

import java.util.List;

public interface AvaliacaoService {

    void avaliar( int idConsulta, int estrelas, String comentario);

    int gerarNovoId();

    double mediaEstrelas(int idMedico);
}
