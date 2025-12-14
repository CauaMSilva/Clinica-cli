package repository.interfaces;

import models.entities.Medico;
import java.util.List;

public interface MedicoRepository {
    void salvar(Medico medico);

    List<Medico> listar();

    Medico buscarPorId(int id);

    Medico buscarPorNome(String nome);

    List<Medico> listarPorPlano(String plano);

    void deletar(int id);

    void atualizar(Medico medico);

    int gerarNovoId();
}
