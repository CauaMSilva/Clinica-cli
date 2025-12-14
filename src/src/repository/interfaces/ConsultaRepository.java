package repository.interfaces;

import models.entities.Consulta;
import java.util.List;

public interface ConsultaRepository {

    void salvar(Consulta consulta);

    void atualizar(Consulta consulta);

    List<Consulta> listar();

    Consulta buscarPorId(int id);

    int gerarNovoId();
}
