package services.interfaces;

import models.entities.Medico;
import java.util.List;

public interface MedicoService {

    void cadastrar(Medico medico);

    Medico buscarPorId(int id);

    Medico buscarPorNome(String nome);

    List<Medico> listarPorPlano(String plano);

    List<Medico> listar();

    Medico login(String nome);

    void deletar(int id);

    void atualizar(Medico medico);

    int gerarNovoId();
}
