package repository.interfaces;

import models.entities.Receita;
import java.util.List;

public interface ReceitaRepository {

    void salvar(Receita receita);

    List<Receita> listar();

    Receita buscarPorId(int id);
}
