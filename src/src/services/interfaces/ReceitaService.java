package services.interfaces;

import models.entities.Receita;
import java.util.List;

public interface ReceitaService {

    void cadastrar(Receita receita);

    Receita buscarPorId(int id);

    List<Receita> listar();

    int gerarNovoId();
}
