package repository.interfaces;

import models.entities.Avaliacao;
import java.util.List;

public interface AvaliacaoRepository {

    void salvar(Avaliacao avaliacao);

    List<Avaliacao> listar();

    List<Avaliacao> listarPorMedico(int idMedico);

    int gerarNovoId();

    double mediaEstrelas(int idMedico);
}
